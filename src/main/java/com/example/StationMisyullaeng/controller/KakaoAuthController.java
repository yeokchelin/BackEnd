package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth/kakao")
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws IOException {
        String jwt = kakaoAuthService.kakaoLogin(code);
        String redirectUrl = "http://localhost:5173/kakao/token?token=" + jwt;
        response.sendRedirect(redirectUrl);
    }
}
