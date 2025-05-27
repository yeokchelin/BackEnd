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

    @Column(name = "name", nullable = false, length = 100)
    private String name;        //가게 이름

    @Column(name = "category", length = 50)
    private String category;    //가게 카테고리(ex: 이자카야, 포장마차, 요리주점 등등)

    @Column(name = "address", nullable = false, length = 255)
    private String address;     //가게 주소

    @Column(name = "phone", length = 20)
    private String phone;       //가게 전화번호

    @Column(name = "open", length = 10)
    private LocalTime open;     //오픈시간

    @Column(name = "close", length = 10)
    private LocalTime close;    //마감시간

    @Column(name = "rating")
    private Float rating;       //별점

    // 가게 이미지 및 메뉴 이미지 테이블과 연결, 1:N 관계
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreImage> images = new ArrayList<>();
}