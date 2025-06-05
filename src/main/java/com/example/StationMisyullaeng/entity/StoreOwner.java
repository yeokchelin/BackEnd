package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 반드시 필요

    private String kakaoId;
    private String name;
}



