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
    private String phone;

    @Column(name = "description")
    private String description;

    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "contact")
    private String contact;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "category")
    private String category;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "meeting_station", length = 50)
    private String meetingStation;

    // 편의 메서드
    public void updateInfo(String name, String address, String hours, String contact,
                           String description, String registrationNumber, String category, String imageUrl,
                           String meetingStation) {
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.contact = contact;
        this.description = description;
        this.registrationNumber = registrationNumber;
        this.category = category;
        this.imageUrl = imageUrl;
        this.meetingStation = meetingStation;
    }
}
