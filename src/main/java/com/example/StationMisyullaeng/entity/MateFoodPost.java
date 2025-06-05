package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_mate_food")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MateFoodPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    // KakaoUser와의 관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private KakaoUser user;

    // ★★★ writer 필드 유지 (DB 스키마에 존재) ★★★
    @Column(nullable = false, length = 50)
    private String writer; // 작성자 닉네임

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "meeting_station", length = 50)
    private String meetingStation;

    @Column(name = "meeting_time", length = 50)
    private String meetingTime;

    @Column(name = "recruit_count")
    private Integer recruitCount;

    @Column(name = "preferred_gender", length = 10)
    private String preferredGender;

    @Column(length = 20, nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // ★★★ KakaoUser 엔티티와의 관계 (DB 스키마에 kakao_user_id 컬럼 존재) ★★★
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kakao_user_id", nullable = false)
    private KakaoUser kakaoUser; // ❗️ KakaoUser 엔티티의 실제 패키지 경로를 임포트하세요.

    // 게시글 수정 시 호출될 메서드 (필요하다면)
    public void update(String title, String content, String meetingStation, String meetingTime,
                       Integer recruitCount, String preferredGender, String status) {
        this.title = title;
        this.content = content;
        this.meetingStation = meetingStation;
        this.meetingTime = meetingTime;
        this.recruitCount = recruitCount;
        this.preferredGender = preferredGender;
        this.status = status;
        // updatedAt은 @UpdateTimestamp에 의해 자동으로 갱신됩니다.
    }
}