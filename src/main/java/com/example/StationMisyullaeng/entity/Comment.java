package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments") // 모든 댓글이 저장될 테이블명
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 댓글 대상의 ID (예: 자유게시글 ID, 밥친구 게시글 ID, 식당 ID 등)
    @Column(nullable = false)
    private Long targetId;

    // 댓글 대상의 타입 (예: "FREE_BOARD_POST", "MEAL_POST", "RESTAURANT_REVIEW")
    @Column(nullable = false, length = 50)
    private String targetType;

    // 댓글 작성자 KakaoUser 엔티티와의 다대일(ManyToOne) 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kakao_user_id", nullable = false) // comments 테이블에 kakao_user_id 컬럼으로 매핑
    private KakaoUser kakaoUser; // ❗️ KakaoUser 엔티티의 실제 패키지 경로를 임포트하세요.

    @Column(nullable = false)
    private LocalDateTime createdAt;
}