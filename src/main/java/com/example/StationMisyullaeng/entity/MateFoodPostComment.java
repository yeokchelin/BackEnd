// src/main/java/com/example/StationMisyullaeng/entity/MateFoodPostComment.java
package com.example.StationMisyullaeng.entity; // 실제 프로젝트 패키지 경로로 수정하세요.

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "mate_food_comment") // 테이블명 소문자 스네이크 케이스 권장 (또는 기존대로 MateFoodComment)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MateFoodPostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference // 양방향 연관관계 시 무한 루프 방지
    private MateFoodPost mateFoodPost; // 연관된 게시글

    @Column(nullable = false, length = 50)
    private String writer; // 작성자 닉네임

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @CreationTimestamp // 엔티티 생성 시 자동으로 현재 시간 저장
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // 엔티티 수정 시 자동으로 현재 시간 저장
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}