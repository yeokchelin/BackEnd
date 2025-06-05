package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "hours")
    private String hours;

    @Column(name = "phone")
    private String phone; // 현재 프론트엔드에서는 'contact'를 사용하므로 이 필드의 실제 사용 여부 확인 필요.

    @Column(name = "description")
    private String description;

    @Column(name = "kakao_id")
    private String kakaoId; // 추가: 점주 식별용 kakaoId

    @Column(name = "contact")
    private String contact; // 프론트엔드에서 'contact'로 보냄

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "category") // 카테고리 필드
    private String category;

    @Column(name = "image_url", length = 500) // 이미지 URL 필드 (URL은 길 수 있으므로 길이 넉넉하게)
    private String imageUrl;

    // ★★★ meetingStation 필드 추가 ★★★
    @Column(name = "meeting_station", length = 50) // 만날 역 (지하철역 이름)
    private String meetingStation;


    // 편의 메서드: 가게 정보 업데이트
    public void updateInfo(String name, String address, String hours, String contact,
                           String description, String registrationNumber, String category, String imageUrl,
                           String meetingStation) { // ★★★ meetingStation 파라미터 추가 ★★★
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.contact = contact;
        this.description = description;
        this.registrationNumber = registrationNumber;
        this.category = category;
        this.imageUrl = imageUrl;
        this.meetingStation = meetingStation; // ★★★ meetingStation 업데이트 ★★★
        // phone 필드는 업데이트 시 사용 여부 확인 필요
    }
}