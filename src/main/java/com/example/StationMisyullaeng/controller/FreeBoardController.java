package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.FreePostDto; // ❗️ FreePostDto 임포트
import com.example.StationMisyullaeng.entity.FreePostWrite;
import com.example.StationMisyullaeng.service.FreeBoardPostService; // ❗️ FreeBoardPostService 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // ❗️ ResponseEntity 임포트
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freeboard") // 기본 경로
@RequiredArgsConstructor
public class FreeBoardController { // ❗️ 클래스 이름 변경

    private final FreeBoardPostService freeBoardPostService; // ❗️ FreeBoardPostService 주입

    // 1. 새 자유 게시글 작성 (POST)
    // POST /api/freeboard/write
    @PostMapping("/write") // ❗️ 경로 변경
    public ResponseEntity<FreePostDto> createFreePost(@RequestBody FreePostDto freePostDto) { // ❗️ DTO 사용, 반환 타입 변경
        FreePostDto createdPost = freeBoardPostService.createFreePost(freePostDto); // ❗️ 서비스 메서드 호출
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost); // ❗️ 201 Created 응답
    }

    // 2. 모든 자유 게시글 목록 조회 (GET)
    // GET /api/freeboard/posts
    @GetMapping("/posts") // ❗️ 경로 변경
    public ResponseEntity<List<FreePostDto>> getAllFreePosts() { // ❗️ 반환 타입 변경
        List<FreePostDto> posts = freeBoardPostService.getAllFreePosts(); // ❗️ 서비스 메서드 호출
        return ResponseEntity.ok(posts); // ❗️ 200 OK 응답
    }

    // 3. 특정 ID의 자유 게시글 조회 (GET)
    // GET /api/freeboard/posts/{postId}
    @GetMapping("/posts/{postId}") // ❗️ 경로 추가
    public ResponseEntity<FreePostDto> getFreePostById(@PathVariable Long postId) { // ❗️ 반환 타입 변경
        try {
            FreePostDto post = freeBoardPostService.getPostById(postId); // ❗️ 서비스 메서드 호출
            return ResponseEntity.ok(post); // ❗️ 200 OK 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // ❗️ 404 Not Found 응답
        }
    }

    // 4. 게시글 수정 (PUT)
    // PUT /api/freeboard/posts/{postId}
    @PutMapping("/posts/{postId}") // ❗️ 경로 변경
    public ResponseEntity<FreePostDto> updateFreePost(@PathVariable Long postId, @RequestBody FreePostDto updatedPostDto) { // ❗️ DTO 사용, 반환 타입 변경
        try {
            FreePostDto updatedPost = freeBoardPostService.updatePost(postId, updatedPostDto); // ❗️ 서비스 메서드 호출
            return ResponseEntity.ok(updatedPost); // ❗️ 200 OK 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // ❗️ 404 Not Found 응답
        }
    }

    // 5. 게시글 삭제 (DELETE)
    // DELETE /api/freeboard/posts/{postId}
    @DeleteMapping("/posts/{postId}") // ❗️ 경로 변경
    public ResponseEntity<Void> deleteFreePost(@PathVariable Long postId) { // ❗️ 반환 타입 변경
        freeBoardPostService.deletePost(postId); // ❗️ 서비스 메서드 호출
        return ResponseEntity.noContent().build(); // ❗️ 204 No Content 응답 (성공적인 삭제)
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FreePostWrite>> getPostsByUser(@PathVariable Long userId) {
        List<FreePostWrite> posts = freeBoardPostService.findPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }
}