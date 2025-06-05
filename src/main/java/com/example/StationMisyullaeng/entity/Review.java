package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    // KakaoUser와의 관계 추가
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private KakaoUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false) // ⭐ FK 컬럼명 변경: store_id -> restaurant_id
    private Restaurant restaurant;    // ⭐ 타입 변경: Store -> Restaurant

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "rate", nullable = false)
    private Integer rate;

<<<<<<< HEAD
    @Column(name = "image_path", length = 255)
    private String imagePath;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
=======

>>>>>>> f943e95 (양방향 관계 추가)
}
