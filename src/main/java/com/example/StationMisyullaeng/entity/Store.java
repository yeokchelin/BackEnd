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
    private String kakaoId; // 추가: 점주 식별용 kakaoId

    @Column(name = "contact")
    private String contact;

    @Column(name = "registration_number")
    private String registrationNumber;

}
