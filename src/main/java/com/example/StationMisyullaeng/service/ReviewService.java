package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.ReviewRequestDto;
import com.example.StationMisyullaeng.dto.ReviewResponseDto;
import com.example.StationMisyullaeng.entity.Review;
import com.example.StationMisyullaeng.entity.Restaurant; // ⭐ Restaurant 엔티티 임포트
import com.example.StationMisyullaeng.repository.ReviewRepository;
import com.example.StationMisyullaeng.repository.RestaurantRepository; // ⭐ RestaurantRepository 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository; // ⭐ RestaurantRepository 주입

    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + id));
        return ReviewResponseDto.toDto(review);
    }

    // 특정 restaurantId에 대한 리뷰 목록 조회 메서드
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByRestaurantId(Long restaurantId) { // ⭐ 파라미터명 변경
        // ⭐ 변경: findByRestaurant_Id를 사용하여 ReviewRepository에서 리뷰 목록을 가져옵니다.
        List<Review> reviews = reviewRepository.findByRestaurant_Id(restaurantId);
        return reviews.stream()
                .map(ReviewResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewResponseDto writeReview(ReviewRequestDto reviewRequestDto) {
        // ⭐ 중요: reviewRequestDto의 Long restaurantId를 사용하여 Restaurant 엔티티를 조회합니다.
        Restaurant restaurant = restaurantRepository.findById(reviewRequestDto.getRestaurantId()) // ⭐ findById 사용
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + reviewRequestDto.getRestaurantId()));

        // ReviewRequestDto를 Review 엔티티로 변환할 때 조회된 Restaurant 객체를 넘겨줍니다.
        Review review = reviewRequestDto.toEntity(restaurant);
        Review savedReview = reviewRepository.save(review);
        return ReviewResponseDto.toDto(savedReview);
    }
}
