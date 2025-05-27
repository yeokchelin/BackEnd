package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;              // 맛집 이름

    private String stationName;       // 지하철역 이름

    private String category;          // 음식 카테고리

    private Double rating;            // 평점 (예: 4.5)

    private Integer reviewCount;      // 리뷰 수

    private String imageUrl;          // 대표 이미지 URL

    private String description;       // 한 줄 소개
}