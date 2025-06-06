package com.example.StationMisyullaeng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private Long targetId;
    private String targetType;
    private Long kakaoUserId; // 댓글 작성자의 KakaoUser ID
    private String writerNickname; // 댓글 작성자의 닉네임 (프론트엔드 표시용, 백엔드에서 채워줌)
    private String writerAvatarUrl; // 댓글 작성자의 아바타 URL (프론트엔드 표시용, 백엔드에서 채워줌)
    private LocalDateTime createdAt;
    private boolean isAuthor; // 현재 로그인된 사용자가 이 댓글의 작성자인지 여부 (프론트엔드에서 버튼 활성화용, 백엔드에서 채워줌)
}