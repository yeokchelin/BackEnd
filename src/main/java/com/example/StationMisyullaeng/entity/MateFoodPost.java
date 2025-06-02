// src/main/java/com/example/StationMisyullaeng/entity/MateFoodPost.java
package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_mate_food") // 테이블명
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

    @Column(nullable = false, length = 50)
    private String writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT") // ❗️ content는 null을 허용하지 않음
    private String content;

    @Column(name = "meeting_station", length = 50, nullable = false) // ❗️ 만날 역도 필수로 가정
    private String meetingStation;

    @Column(name = "meeting_time", length = 50, nullable = false) // ❗️ 만날 시간도 필수로 가정
    private String meetingTime;

    @Column(name = "recruit_count", nullable = false) // ❗️ 모집 인원도 필수로 가정
    private Integer recruitCount;

    @Column(name = "preferred_gender", length = 10, nullable = false) // ❗️ 선호 성별도 필수로 가정
    private String preferredGender;

    @Column(length = 20, nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}