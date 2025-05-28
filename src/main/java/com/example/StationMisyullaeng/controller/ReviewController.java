package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.ReviewRequestDto;
import com.example.StationMisyullaeng.dto.ReviewResponseDto;
import com.example.StationMisyullaeng.entity.Review;
import com.example.StationMisyullaeng.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //reviewId로 리뷰보기
    @GetMapping("/reviewId/{id}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }


    //리뷰작성
    @PostMapping
    public ResponseEntity<ReviewResponseDto> writeReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto savedReview = reviewService.writeReview(reviewRequestDto);
        return ResponseEntity.ok(savedReview);
    }



}
