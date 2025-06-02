package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;       //store 테이블의 PK

    @Column(name = "name", nullable = false)
    private String name;        //상호명

    @Column(name = "address", nullable = false)
    private String address;     //주소

    @Column(name = "hours")
    private String hours;       //영업시간

    @Column(name = "phone")
    private String phone;       //연락처

    @Column(name = "description")
    private String description; //가게 소개
    
    @OneToMany(mappedBy = "store",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();   //Menu 테이블과 양방향 연관관계 설정
    
}