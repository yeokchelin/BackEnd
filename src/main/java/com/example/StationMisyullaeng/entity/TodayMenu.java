package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "today_menu") // 테이블 이름은 소문자/스네이크 케이스 권장
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TodayMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl; // 썸네일 이미지 (필요시)

    @Column(nullable = false)
    private String category;
}


