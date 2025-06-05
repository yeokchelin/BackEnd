package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.ReviewRequestDto;
import com.example.StationMisyullaeng.dto.ReviewResponseDto;
import com.example.StationMisyullaeng.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // reviewId로 리뷰보기
    @GetMapping("/reviewId/{id}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    // ⭐ 변경: 특정 restaurantId에 대한 리뷰 목록 조회
    // 프론트엔드의 axios.get(`${API_BASE_URL}/restaurant/{restaurantId}`); 와 일치합니다.
    @GetMapping("/restaurant/{restaurantId}") // ⭐ 경로 변경: /store/{storeId} -> /restaurant/{restaurantId}
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByRestaurantId(@PathVariable Long restaurantId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByRestaurantId(restaurantId);
        return ResponseEntity.ok(reviews);
    }

    // 리뷰작성
    @PostMapping
    public ResponseEntity<ReviewResponseDto> writeReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto savedReview = reviewService.writeReview(reviewRequestDto);
        return ResponseEntity.ok(savedReview);
    }
}
