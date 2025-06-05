package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.entity.UserGrade;
import com.example.StationMisyullaeng.repository.KakaoUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final KakaoUserRepository memberRepository; // memberRepository는 KakaoUserRepository입니다.
    private final RestTemplate restTemplate = new RestTemplate();

    public String kakaoLogin(String code) {
        // 1. 액세스 토큰 발급
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        String accessToken = (String) response.getBody().get("access_token");

        // 2. 사용자 정보 요청
        HttpHeaders infoHeaders = new HttpHeaders();
        infoHeaders.setBearerAuth(accessToken);
        HttpEntity<?> infoRequest = new HttpEntity<>(infoHeaders);

        ResponseEntity<Map> userInfo = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                infoRequest,
                Map.class
        );

        Map<String, Object> body = userInfo.getBody();
        String kakaoId = String.valueOf(body.get("id"));

        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        // 3. 사용자 정보 DB 저장 (중복 방지)
        KakaoUser member = memberRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> {
                    KakaoUser newMember = KakaoUser.builder()
                            .kakaoId(kakaoId)
                            .email(email)
                            .nickname(nickname)
                            .profileImage(profileImage)
                            .grade(UserGrade.GENERAL)
                            .build();
                    return memberRepository.save(newMember);
                });

        // 4. JWT 생성 후 반환
        // ❗️ member.getKakaoId() (String) 대신 KakaoUser 객체 자체를 전달합니다.
        return createJwt(member);
    }

    // ❗️ 메서드 시그니처 변경: email (String) 대신 KakaoUser 객체를 받습니다.
    private String createJwt(KakaoUser kakaoUser) {
        // JWT 만료 시간 설정 (예시: 1시간)
        long jwtExpirationMs = 3600000; // 1시간 (밀리초)

        return Jwts.builder()
                .setSubject(kakaoUser.getKakaoId()) // 'sub' 클레임은 카카오 고유 ID (String)
                // ❗️❗️❗️ 핵심 수정: 'userId', 'nickname', 'profileImage' 클레임을 추가합니다. ❗️❗️❗️
                .claim("userId", kakaoUser.getId())             // KakaoUser의 DB ID (Long)
                .claim("nickname", kakaoUser.getNickname())     // 사용자 닉네임
                .claim("profileImage", kakaoUser.getProfileImage()) // 프로필 이미지 URL

                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 1시간
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes()) // jwtSecret.getBytes()는 안전한 방식입니다.
                .compact();
    }
}