package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.ReviewRequestDto;
import com.example.StationMisyullaeng.dto.ReviewResponseDto;
import com.example.StationMisyullaeng.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews") // ★★★ 이 부분이 /api/reviews 인지 확인 ★★★
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    // ★★★ 이 부분이 /byStore/{storeId} 인지 확인 ★★★
    @GetMapping("/byStore/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(reviewService.getReviewsByStoreId(storeId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> writeReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        return new ResponseEntity<>(reviewService.writeReview(reviewRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{reviewId}/reply")
    public ResponseEntity<ReviewResponseDto> addOwnerReply(
            @PathVariable Long reviewId,
            @RequestBody String replyContent
    ) {
        return ResponseEntity.ok(reviewService.addOwnerReply(reviewId, replyContent));
    }
}