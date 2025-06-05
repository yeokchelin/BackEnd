package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.entity.KakaoUser; // KakaoUser 엔티티를 직접 사용하므로 임포트 필요
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatePostDto {
    private Long id;
    // ★★★ writer 필드 유지 ★★★
    private String writer; // 게시글 작성자 닉네임 (DB 컬럼과 매핑, 프론트엔드 표시용)

    private String title;
    private String content;
    private String meetingStation;
    private String meetingTime;
    private Integer recruitCount;
    private String preferredGender;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- 추가 필드 (유지) ---
    private Long kakaoUserId; // 게시글 작성자의 KakaoUser ID
    private String writerAvatarUrl; // 게시글 작성자의 아바타 URL
    // ---------------

    // Entity -> DTO 변환 메서드
    public static MatePostDto fromEntity(MateFoodPost entity) {
        // null 체크를 통해 NullPointerException 방지
        Long userId = (entity.getKakaoUser() != null) ? entity.getKakaoUser().getId() : null;
        // writer 필드는 KakaoUser 닉네임을 우선하고, KakaoUser가 없으면 entity.getWriter() 사용
        String nickname = (entity.getKakaoUser() != null) ? entity.getKakaoUser().getNickname() : entity.getWriter();
        String avatarUrl = (entity.getKakaoUser() != null) ? entity.getKakaoUser().getProfileImage() : null;

        return MatePostDto.builder()
                .id(entity.getId())
                .writer(nickname) // ★★★ writer 필드를 KakaoUser 닉네임 또는 기존 writer로 채움 ★★★
                .title(entity.getTitle())
                .content(entity.getContent())
                .meetingStation(entity.getMeetingStation())
                .meetingTime(entity.getMeetingTime())
                .recruitCount(entity.getRecruitCount())
                .preferredGender(entity.getPreferredGender())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                // --- 추가된 필드 매핑 ---
                .kakaoUserId(userId)
                .writerAvatarUrl(avatarUrl)
                // -----------------------
                .build();
    }

    // DTO -> Entity 변환 메서드 (주로 생성/수정 시 사용)
    public MateFoodPost toEntity() {
        return MateFoodPost.builder()
                // ★★★ writer 필드도 DTO에서 엔티티로 전달 ★★★
                .writer(this.writer) // DTO의 writer 값을 엔티티의 writer 필드로 매핑
                .title(this.title)
                .content(this.content)
                .meetingStation(this.meetingStation)
                .meetingTime(this.meetingTime)
                .recruitCount(this.recruitCount)
                .preferredGender(this.preferredGender)
                .status(this.status)
                // createdAt, updatedAt, kakaoUser는 여기서 설정하지 않음 (서비스에서 처리)
                .build();
    }
}