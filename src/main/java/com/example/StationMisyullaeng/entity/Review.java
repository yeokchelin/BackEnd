package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;  // 리뷰 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store storeId;    // Store 엔티티와 N:1 관계

    @Column(name = "title", nullable = false, length = 100)
    private String title;   // 리뷰 제목

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 리뷰 내용

    @Column(name = "rate", nullable = false)
    private Integer rate;   // 별점 (0~5)

}
