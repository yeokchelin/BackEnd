package com.example.StationMisyullaeng.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;        //각 메뉴 별 PK

    @Column(name = "menu_name", nullable = false)
    private String menuName;    //메뉴이름

    @Column(name = "price", nullable = false)
    private Integer price;      //가격

    @Column(name = "menu_description", nullable = false)
    private String menuDescription; //메뉴 설명

    @Column(name = "img_url")
    private String imgUrl;      //메뉴 이미지 URL

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;        //store 기본키 참조
}
