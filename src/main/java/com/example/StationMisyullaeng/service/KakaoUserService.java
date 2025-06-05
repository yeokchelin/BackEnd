package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.entity.UserGrade;
import com.example.StationMisyullaeng.repository.KakaoUserRepository;
import com.example.StationMisyullaeng.repository.StoreOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoUserService {

    private final KakaoUserRepository kakaoUserRepository;
    private final StoreOwnerRepository storeOwnerRepository; // 추가

    @Transactional(readOnly = true)
    public UserGrade getUserGrade(String kakaoId) {
        KakaoUser user = kakaoUserRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + kakaoId));
        return user.getGrade();
    }

    @Transactional
    public void updateUserGrade(String kakaoId, UserGrade newGrade) {
        KakaoUser user = kakaoUserRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + kakaoId));
        user.setGrade(newGrade);
        // save 생략 가능: JPA는 트랜잭션 커밋 시 dirty checking으로 자동 반영
    }
}
