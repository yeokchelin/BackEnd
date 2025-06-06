// src/main/java/com/example/StationMisyullaeng/controller/KakaoAuthController.java
package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder; // ❗️ URLEncoder 임포트
import java.nio.charset.StandardCharsets; // ❗️ StandardCharsets 임포트

@RestController
@RequestMapping("/api/auth/kakao")
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws IOException {
        String jwt = kakaoAuthService.kakaoLogin(code);

        // ❗️❗️❗️ JWT 토큰을 URL 인코딩합니다. ❗️❗️❗️
        String encodedJwt = URLEncoder.encode(jwt, StandardCharsets.UTF_8.toString());

        // 인코딩된 JWT를 URL에 포함하여 구성합니다.
        String redirectUrl = "http://localhost:5173/kakao/token?token=" + encodedJwt;

        System.out.println("Backend Redirecting to: " + redirectUrl);

        response.sendRedirect(redirectUrl);
    }
}