// src/main/java/com/example/StationMisyullaeng/controller/MateFoodPostCommentController.java
package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.CommentRequestDto;
import com.example.StationMisyullaeng.dto.CommentResponseDto;
import com.example.StationMisyullaeng.service.MateFoodPostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@RestController
@RequestMapping("/mateFoodPost/{postId}/comments")
@RequiredArgsConstructor
public class MateFoodPostCommentController {

    private final MateFoodPostCommentService commentService;

    // ... (createComment, getCommentsByPostId 메서드는 이전과 동일)
    @PostMapping public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) { /* 이전과 동일 */
        return null;
    }
    @GetMapping public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId) { /* 이전과 동일 */
        return null;
    }


    // ❗️ 추가: 댓글 수정 엔드포인트 (PUT /mateFoodPost/{postId}/comments/{commentId})
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto) { // 요청 본문으로 수정할 내용 받음
        try {
            // TODO: 실제로는 서비스 계층에서 수정 권한 확인 필요
            CommentResponseDto updatedComment = commentService.updateComment(postId, commentId, requestDto);
            return ResponseEntity.ok(updatedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } //catch (AccessDeniedException e) {
        //  return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        //}
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정 중 오류 발생");
        }
    }

    // 댓글 삭제 엔드포인트 (이전과 동일)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) { /* 이전과 동일 */ }
}