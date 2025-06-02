package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.MatePostDto;
import com.example.StationMisyullaeng.service.MateFoodPostService; // 실제 서비스 클래스명 확인
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // updatePostStatus에서 사용

@RestController
@RequestMapping("/boardmatefood") // 기본 경로: /boardmatefood
@RequiredArgsConstructor
public class MateFoodPostController {

    private final MateFoodPostService mateFoodPostService; // 서비스 주입

    // ❗️❗️ 추가: 전체 게시글 조회 (GET /boardmatefood/posts) ❗️❗️
    @GetMapping("/posts")
    public ResponseEntity<List<MatePostDto>> getAllPosts() {
        List<MatePostDto> posts = mateFoodPostService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 특정 ID의 게시글 조회 (GET /boardmatefood/posts/{postId})
    // (이 메서드는 이미 존재한다고 가정, 없다면 이것도 추가 필요)
    @GetMapping("/posts/{postId}")
    public ResponseEntity<MatePostDto> getPostById(@PathVariable Long postId) {
        try {
            MatePostDto post = mateFoodPostService.getPostById(postId);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 새 게시글 작성 (POST /boardmatefood/write)
    @PostMapping("/write")
    public ResponseEntity<?> createPost(@RequestBody MatePostDto matePostDto) {
        try {
            MatePostDto createdPost = mateFoodPostService.createPost(matePostDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성 중 오류가 발생했습니다.");
        }
    }

    // 게시글 전체 수정 (PUT /boardmatefood/posts/{postId})
    // (이 메서드는 이미 존재한다고 가정, 없다면 이것도 추가 필요)
    @PutMapping("/posts/{postId}")
    public ResponseEntity<MatePostDto> updatePost(@PathVariable Long postId, @RequestBody MatePostDto matePostDto) {
        try {
            MatePostDto updatedPost = mateFoodPostService.updatePost(postId, matePostDto);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 상태만 부분 수정 (PATCH /boardmatefood/posts/{postId}/status)
    // (이 메서드는 이미 존재한다고 가정, 없다면 이것도 추가 필요)
    @PatchMapping("/posts/{postId}/status")
    public ResponseEntity<MatePostDto> updatePostStatus(
            @PathVariable Long postId,
            @RequestBody Map<String, String> payload) {
        try {
            String newStatus = payload.get("status");
            if (newStatus == null || newStatus.trim().isEmpty() ||
                    (!"모집 중".equals(newStatus) && !"모집 완료".equals(newStatus))) {
                return ResponseEntity.badRequest().build();
            }
            MatePostDto updatedPost = mateFoodPostService.updatePostStatus(postId, newStatus);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error updating post status for ID " + postId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 게시글 삭제 (DELETE /boardmatefood/posts/{postId})
    // (이 메서드는 이미 존재한다고 가정, 없다면 이것도 추가 필요)
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        try {
            mateFoodPostService.deletePost(postId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}