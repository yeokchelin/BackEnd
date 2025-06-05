package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kakaoId;        // 카카오 사용자 고유 ID
    private String email;
    private String nickname;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGrade grade = UserGrade.GENERAL;

}
