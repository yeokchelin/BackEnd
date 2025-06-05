// src/main/java/com/example/StationMisyullaeng/dto/MatePostDto.java
package com.example.StationMisyullaeng.dto; // 실제 프로젝트 패키지 경로로 수정하세요.

import com.example.StationMisyullaeng.entity.MateFoodPost;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatePostDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String meetingStation;
    private String meetingTime;
    private Integer recruitCount;
    private String preferredGender;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Entity -> DTO 변환 메서드
    public static MatePostDto fromEntity(MateFoodPost entity) {
        return MatePostDto.builder()
                .id(entity.getId())
                .writer(entity.getWriter())
                .title(entity.getTitle())
                .content(entity.getContent())
                .meetingStation(entity.getMeetingStation())
                .meetingTime(entity.getMeetingTime())
                .recruitCount(entity.getRecruitCount())
                .preferredGender(entity.getPreferredGender())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // DTO -> Entity 변환 메서드 (주로 생성/수정 시 사용)
    public MateFoodPost toEntity() {
        return MateFoodPost.builder()
                // id는 DB에서 자동 생성되므로 toEntity에서는 보통 설정하지 않거나,
                // 업데이트 시에는 기존 id를 사용해야 함. 여기서는 생성 시를 가정.
                .writer(this.writer)
                .title(this.title)
                .content(this.content)
                .meetingStation(this.meetingStation)
                .meetingTime(this.meetingTime)
                .recruitCount(this.recruitCount)
                .preferredGender(this.preferredGender)
                .status(this.status) // 상태 값도 DTO에서 받을 수 있도록
                // createdAt, updatedAt은 DB나 JPA Auditing으로 자동 관리되므로 설정 안 함
                .build();
    }
}