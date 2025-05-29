package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.ReviewRequestDto;
import com.example.StationMisyullaeng.dto.ReviewResponseDto;
import com.example.StationMisyullaeng.entity.Review;
import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.repository.ReviewRepository;
import com.example.StationMisyullaeng.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    //리뷰 보기
    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("리뷰 없음"));

        return ReviewResponseDto.builder()
            .reviewId(review.getReviewId())
            .title(review.getTitle())
            .content(review.getContent())
            .rate(review.getRate())
            .storeId(review.getStoreId().getStoreId())
            .storeName(review.getStoreId().getName())
            .build();
    }

    //리뷰작성
    @Transactional
    public ReviewResponseDto writeReview(ReviewRequestDto dto) {
        Store store = storeRepository.findById(dto.getStoreId())
            .orElseThrow(() -> new RuntimeException("가게 없음"));

        Review review = Review.builder()
            .title(dto.getTitle())
            .content(dto.getContent())
            .rate(dto.getRate())
            .storeId(store)
            .build();

        Review savedReview = reviewRepository.save(review);

        return ReviewResponseDto.builder()
            .reviewId(savedReview.getReviewId())
            .title(savedReview.getTitle())
            .content(savedReview.getContent())
            .rate(savedReview.getRate())
            .storeId(store.getStoreId())
            .storeName(store.getName())
            .build();
    }

}
