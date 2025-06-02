package com.example.StationMisyullaeng.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long favoriteId;    //favorite 테이블 기본키

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;          //user 테이블의 user_id

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;        //store 테이블의 store_id
}