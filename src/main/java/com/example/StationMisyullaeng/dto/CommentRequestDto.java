// src/main/java/com/example/StationMisyullaeng/dto/CommentRequestDto.java
package com.example.StationMisyullaeng.dto; // 실제 프로젝트 패키지 경로로 수정하세요.

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok 어노테이션 추가 가능: @AllArgsConstructor, @Builder 등
@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    // postId는 URL 경로 변수로 받으므로 DTO에 포함하지 않음
    // 또는 요청 본문에 포함하고 싶다면 추가 가능: private Long postId;

    private String writer;  // 댓글 작성자 (실제로는 인증 정보에서 가져와야 함)
    private String content; // 댓글 내용

    // 엔티티 변환 로직은 서비스 레이어에서 처리하거나,
    // 여기에 toEntity(MateFoodPost post) 와 같은 메서드를 만들어도 됩니다.
    // MateFoodPostComment toEntity(MateFoodPost post) {
    //     return MateFoodPostComment.builder()
    //             .writer(this.writer)
    //             .content(this.content)
    //             .mateFoodPost(post)
    //             .build();
    // }
}