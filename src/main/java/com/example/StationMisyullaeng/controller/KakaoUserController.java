package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.UserGrade;
import com.example.StationMisyullaeng.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;

    @GetMapping("/{kakaoId}/grade")
    public UserGrade getUserGrade(@PathVariable String kakaoId) {
        return kakaoUserService.getUserGrade(kakaoId);
    }

    @PostMapping("/{kakaoId}/grade")
    public void updateUserGrade(@PathVariable String kakaoId, @RequestParam UserGrade grade) {
        kakaoUserService.updateUserGrade(kakaoId, grade);
    }
}