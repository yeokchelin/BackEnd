package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.MatePostDto; // 실제 DTO 경로로 수정하세요.
import com.example.StationMisyullaeng.service.MateFoodPostService; // 실제 서비스 경로로 수정하세요.
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Map을 사용하기 위해 import 추가

@RestController
@RequestMapping("/api/boardmatefood") // 프론트엔드의 API_BASE_URL과 일치하도록 "/api"를 유지합니다.
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
    @PostMapping("/write")
    public ResponseEntity<MatePostDto> createPost(@RequestBody MatePostDto matePostDto) {
        MatePostDto createdPost = mateService.createPost(matePostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // 게시글 수정 (PUT 요청)
    @PutMapping("/posts/{postId}")
    public ResponseEntity<MatePostDto> updatePost(@PathVariable Long postId, @RequestBody MatePostDto matePostDto) {
        try {
            MatePostDto updatedPost = mateService.updatePost(postId, matePostDto);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        try {
            mateService.deletePost(postId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 새로운 엔드포인트: 게시글 상태 변경 (PATCH 요청)
    @PatchMapping("/posts/{postId}/status") // 이 부분이 추가되었습니다.
    public ResponseEntity<MatePostDto> updatePostStatus(
            @PathVariable Long postId,
            @RequestBody Map<String, String> payload) {
        try {
            String newStatus = payload.get("status");
            if (newStatus == null || newStatus.isEmpty()) {
                return ResponseEntity.badRequest().build(); // "status" 필드가 누락되었거나 비어있는 경우
            }
            MatePostDto updatedPost = mateService.updatePostStatus(postId, newStatus);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            // 게시글을 찾을 수 없거나, 서비스 계층에서 유효성 검사 실패 시
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // 그 외 예기치 않은 오류 발생 시
            System.err.println("Error updating post status: " + e.getMessage()); // 서버 로그에 자세한 에러 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}