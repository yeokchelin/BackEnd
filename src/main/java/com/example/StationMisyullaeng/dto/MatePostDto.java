// src/main/java/com/example/StationMisyullaeng/dto/MatePostDto.java
package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.MateFoodPost;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatePostDto {
    private Long id; // 응답 시 사용
    private String writer;
    private String title;
    private String content; // ❗️ 이 필드가 프론트의 postContent와 매핑됨
    private String meetingStation;
    private String meetingTime;
    private Integer recruitCount;
    private String preferredGender;
    private String status;
    private LocalDateTime createdAt; // 응답 시 사용
    private LocalDateTime updatedAt; // 응답 시 사용

    public static MatePostDto fromEntity(MateFoodPost entity) {
        if (entity == null) return null;
        return MatePostDto.builder()
                .id(entity.getId())
                .writer(entity.getWriter())
                .title(entity.getTitle())
                .content(entity.getContent()) // ❗️ 엔티티의 content 사용
                .meetingStation(entity.getMeetingStation())
                .meetingTime(entity.getMeetingTime())
                .recruitCount(entity.getRecruitCount())
                .preferredGender(entity.getPreferredGender())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public MateFoodPost toEntity() {
        return MateFoodPost.builder()
                .writer(this.writer)
                .title(this.title)
                .content(this.content) // ❗️ DTO의 content를 엔티티로 전달
                .meetingStation(this.meetingStation)
                .meetingTime(this.meetingTime)
                .recruitCount(this.recruitCount)
                .preferredGender(this.preferredGender)
                .status(this.status)
                .build();
    }
}