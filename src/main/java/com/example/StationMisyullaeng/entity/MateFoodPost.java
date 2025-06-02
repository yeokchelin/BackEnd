package com.example.StationMisyullaeng.entity;

import com.example.StationMisyullaeng.controller.MateFoodPostController;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "boardmatefood")
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
    private String content; // 내용

    @Column(name = "meeting_station", length = 50)
    private String meetingStation; // 만날 역

    @Column(name = "meeting_time", length = 10)
    private String meetingTime;  // 예: "18:30" (문자열로 받음)

    @Column(name = "recruit_count")
    private String recruitCount; // 같이먹을사람 구하는 인원

    @Column(name = "preferred_gender", length = 10)
    private String preferredGender;  // 예: "무관", "여성", "남성"

    @OneToMany(mappedBy = "mateFoodPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MateFoodPostComment> comments;

}