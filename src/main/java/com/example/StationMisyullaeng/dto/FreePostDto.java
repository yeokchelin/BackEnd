package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.FreePostWrite; // ❗️ PostWrite 엔티티 임포트
import lombok.*;

import java.time.LocalDateTime; // ❗️ LocalDateTime 임포트

@Getter @Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 포함하는 생성자
@Builder // 빌더 패턴 사용
public class FreePostDto {
    private Long id;
    private String writer;     // 작성자 (닉네임 등)
    private String title;      // 게시글 제목
    private String content;    // 게시글 내용
    private LocalDateTime createdAt; // 생성 시간
    private Long kakaoUserId;

    // ❗️ PostWrite 엔티티를 FreePostDto로 변환하는 정적 팩토리 메서드
    // PostService에서 이 메서드를 사용하고 있습니다.
    public static FreePostDto fromEntity(FreePostWrite freePostWrite) {
        return FreePostDto.builder()
                .id(freePostWrite.getId())
                .writer(freePostWrite.getWriter())
                .title(freePostWrite.getTitle())
                .content(freePostWrite.getContent())
                .createdAt(freePostWrite.getCreatedAt()) // PostWrite 엔티티에 createdAt 필드가 있다면 포함
                .build();
    }
}