package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_image")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class StoreImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;       //store_image 테이블의 PK

    @Column(name = "image_url", nullable = false)
    private String imageUrl;    //이미지 url(사진을 직접 저장하는 것보다 url로 저장하는 것이 좋음)

    @Column(name = "type", nullable = false)
    private String type;        //메뉴 이미지, 가게 이미지 등 이미지의 타입

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;        //Store Entity 클래스와 연결(N:1)

}
