// src/main/java/com/example/StationMisyullaeng/entity/MateFoodPost.java
package com.example.StationMisyullaeng.entity; // 실제 프로젝트 패키지 경로로 수정하세요.

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_mate_food") // 테이블명 (기존 로그에서는 boardmatefood 였으나, board_mate_food가 더 일반적)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본 생성자를 필요로 함
@AllArgsConstructor
@Builder
public class MateFoodPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스의 AUTO_INCREMENT 사용
    @Column(name = "post_id")
    private Long id;

    // KakaoUser와의 관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private KakaoUser user;

    @Column(nullable = false, length = 50)
    private String writer; // 작성자 닉네임

    @Column(nullable = false, length = 100)
    private String title; // 게시글 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 게시글 내용

    @Column(name = "meeting_station", length = 50)
    private String meetingStation; // 만날 역

    @Column(name = "meeting_time", length = 50) // 시간 형식 및 길이를 고려하여 설정
    private String meetingTime;  // 예: "오후 7시", "19:00"

    @Column(name = "recruit_count")
    private Integer recruitCount; // 모집 인원 (본인 제외 인원 또는 총 인원)

    @Column(name = "preferred_gender", length = 10)
    private String preferredGender;  // 예: "무관", "여성", "남성"

    @Column(length = 20, nullable = false)
    private String status; // 게시글 상태 (예: "모집 중", "모집 완료")

    @CreationTimestamp // 엔티티 생성 시 자동으로 현재 시간 저장
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // 엔티티 수정 시 자동으로 현재 시간 저장
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}