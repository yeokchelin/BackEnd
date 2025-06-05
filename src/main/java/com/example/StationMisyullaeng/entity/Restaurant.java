package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
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

    // ★★★ Store 엔티티와의 ManyToOne 관계 추가 ★★★
    // 하나의 Store(가게)는 여러 Restaurant(맛집 정보)를 가질 수 있다고 가정
    // 또는 Restaurant이 Store의 정보임을 나타내는 1:1 관계일 수도 있습니다.
    // 여기서는 일반적인 1:N 관계(Store:Restaurant)를 가정하여 ManyToOne으로 설정합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false) // 'restaurant' 테이블에 'store_id' 컬럼으로 매핑
    private Store store; // ❗️ Store 엔티티의 실제 패키지 경로를 임포트하세요.

}