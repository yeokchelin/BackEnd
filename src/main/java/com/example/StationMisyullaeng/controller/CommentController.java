package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.CommentDto;
import com.example.StationMisyullaeng.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // CORS 설정
@RestController
@RequestMapping("/api/comments") // 모든 댓글 API의 기본 경로
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성 (POST)
    // POST /api/comments
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        try {
            CommentDto createdComment = commentService.createComment(commentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 유효하지 않은 사용자 ID 등
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 대상별 댓글 조회 (GET)
    // GET /api/comments/byTarget/{targetType}/{targetId}?currentKakaoUserId={userId}
    @GetMapping("/byTarget/{targetType}/{targetId}")
    public ResponseEntity<List<CommentDto>> getCommentsByTarget(
            @PathVariable String targetType,
            @PathVariable Long targetId,
            @RequestParam(required = false) Long currentKakaoUserId // 로그인된 사용자 ID (선택 사항, isAuthor 판단용)
    ) {
        try {
            List<CommentDto> comments = commentService.getCommentsByTarget(targetId, targetType, currentKakaoUserId);
            return ResponseEntity.ok(comments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 대상(게시글/식당)을 찾을 수 없을 때 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 댓글 삭제 (DELETE)
    // DELETE /api/comments/{commentId}?kakaoUserId={requestingKakaoUserId}
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long kakaoUserId // 삭제를 요청하는 사용자의 ID (권한 확인용)
    ) {
        try {
            commentService.deleteComment(commentId, kakaoUserId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 권한 없음 (403 Forbidden)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 댓글 수정 (PUT) - 선택 사항
    // PUT /api/comments/{commentId}?kakaoUserId={requestingKakaoUserId}
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentDto commentDto, // content 필드만 포함될 수 있음
            @RequestParam Long kakaoUserId // 수정을 요청하는 사용자의 ID
    ) {
        try {
            CommentDto updatedComment = commentService.updateComment(commentId, commentDto.getContent(), kakaoUserId);
            return ResponseEntity.ok(updatedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 권한 없음 또는 댓글 없음
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}