package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.MatePostDto;
import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.service.MateFoodPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boardmatefood")
@RequiredArgsConstructor
public class MateFoodPostController {

    private final MateFoodPostService mateService;

    // 전체 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<List<MatePostDto>> getAllPosts() {
        List<MatePostDto> posts = mateService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 특정 ID의 게시글 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<MatePostDto> getPostById(@PathVariable Long postId) {
        try {
            MatePostDto post = mateService.getPostById(postId);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 새 게시글 작성
    // POST /api/boardmatefood/write
    @PostMapping("/write")
    public ResponseEntity<MatePostDto> createPost(@RequestBody MatePostDto matePostDto) {
        // matePostDto에는 이미 kakaoUserId가 포함되어 있다고 가정합니다.
        // 서비스 계층에서 이 kakaoUserId를 이용하여 KakaoUser 엔티티를 조회하고 매핑합니다.
        MatePostDto createdPost = mateService.createPost(matePostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // 게시글 수정 (PUT 요청)
    // PUT /api/boardmatefood/posts/{postId}
    @PutMapping("/posts/{postId}")
    public ResponseEntity<MatePostDto> updatePost(
            @PathVariable Long postId,
            @RequestBody MatePostDto matePostDto, // 수정할 내용 (title, content 등)
            @RequestParam Long currentKakaoUserId // ★★★ 현재 로그인된 사용자 ID를 쿼리 파라미터로 받음 ★★★
    ) {
        try {
            // 서비스 계층으로 postId, DTO, 그리고 요청자 ID를 전달
            MatePostDto updatedPost = mateService.updatePost(postId, matePostDto, currentKakaoUserId);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            // 게시글을 찾을 수 없거나, 권한이 없는 경우 (서비스에서 예외 발생)
            if (e.getMessage().contains("권한이 없습니다")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
            }
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            System.err.println("Error updating post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 게시글 삭제
    // DELETE /api/boardmatefood/posts/{postId}
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long currentKakaoUserId // ★★★ 현재 로그인된 사용자 ID를 쿼리 파라미터로 받음 ★★★
    ) {
        try {
            // 서비스 계층으로 postId와 요청자 ID를 전달
            mateService.deletePost(postId, currentKakaoUserId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            // 게시글을 찾을 수 없거나, 권한이 없는 경우 (서비스에서 예외 발생)
            if (e.getMessage().contains("권한이 없습니다")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
            }
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            System.err.println("Error deleting post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 새로운 엔드포인트: 게시글 상태 변경 (PATCH 요청)
    @PatchMapping("/posts/{postId}/status")
    public ResponseEntity<MatePostDto> updatePostStatus(
            @PathVariable Long postId,
            @RequestBody Map<String, String> payload,
            @RequestParam Long currentKakaoUserId // ★★★ 상태 변경 요청 사용자 ID 추가 (권한 확인용) ★★★
    ) {
        try {
            String newStatus = payload.get("status");
            if (newStatus == null || newStatus.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            // 서비스 계층으로 postId, newStatus, 요청자 ID를 전달
            MatePostDto updatedPost = mateService.updatePostStatus(postId, newStatus, currentKakaoUserId);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("권한이 없습니다")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error updating post status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 내가 쓴 게시물 가져오기
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MateFoodPost>> getPostsByUser(@PathVariable Long userId) {
        List<MateFoodPost> posts = mateService.findPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }
}