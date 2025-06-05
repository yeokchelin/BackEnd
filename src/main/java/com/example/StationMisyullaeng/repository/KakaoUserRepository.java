package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, Long> {
    Optional<KakaoUser> findByKakaoId(String kakaoId);
}
