// src/main/java/com/example/StationMisyullaeng/dto/CommentResponseDto.java
package com.example.StationMisyullaeng.dto; // 실제 프로젝트 패키지 경로로 수정하세요.

import com.example.StationMisyullaeng.entity.MateFoodPostComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private Long postId; // 어떤 게시글의 댓글인지 식별
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // private String authorAvatarUrl; // 필요시 추가

    public static CommentResponseDto fromEntity(MateFoodPostComment entity) {
        if (entity == null) return null;
        return CommentResponseDto.builder()
                .id(entity.getId())
                .postId(entity.getMateFoodPost() != null ? entity.getMateFoodPost().getId() : null)
                .writer(entity.getWriter())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt()) // 엔티티에 해당 필드와 getter가 있어야 함
                .updatedAt(entity.getUpdatedAt()) // 엔티티에 해당 필드와 getter가 있어야 함
                .build();
    }
}