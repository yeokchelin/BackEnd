package com.example.StationMisyullaeng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Spring 설정 클래스임을 명시
@EnableWebSecurity // Spring Security를 활성화
public class WebSecurityConfig {

    @Bean // SecurityFilterChain 빈을 정의
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 (API 서버의 경우 흔함)
                .authorizeHttpRequests(authorize -> authorize
                        // ★★★ 모든 "/api/" 경로에 대한 접근을 인증 없이 허용합니다. ★★★
                        .requestMatchers("/api/**").permitAll() // 모든 "/api/" 요청 허용 (개발용)

                        // 그 외 "/api/"로 시작하지 않는 모든 요청 (예: 정적 파일, SPA 라우팅)도 허용합니다.
                        .anyRequest().permitAll()
                );
        // 폼 로그인 및 HTTP Basic 인증 등은 API 서버에서는 보통 사용하지 않으므로 추가하지 않습니다.
        // .formLogin(withDefaults())
        // .httpBasic(withDefaults());

        return http.build();
    }
}