package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MateFoodPost")
@Getter
@Setter
@NoArgsConstructor
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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "meeting_station", length = 50)
    private String meetingStation;

    @Column(name = "meeting_time", length = 10)
    private String meetingTime;  // 예: "18:30" (문자열로 받음)

    @Column(name = "recruit_count")
    private Integer recruitCount;

    @Column(name = "preferred_gender", length = 10)
    private String preferredGender;  // 예: "무관", "여성", "남성"
}