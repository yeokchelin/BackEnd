package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.ReviewRequestDto;
import com.example.StationMisyullaeng.dto.ReviewResponseDto;
import com.example.StationMisyullaeng.entity.Review;
import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.repository.ReviewRepository;
import com.example.StationMisyullaeng.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + id));
        return ReviewResponseDto.toDto(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByStoreId(Long storeId) {
        List<Review> reviews = reviewRepository.findByRestaurant_Store_StoreId(storeId);
        return reviews.stream()
                .map(ReviewResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewResponseDto writeReview(ReviewRequestDto reviewRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(reviewRequestDto.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + reviewRequestDto.getRestaurantId()));

        Review review = reviewRequestDto.toEntity(restaurant);
        Review savedReview = reviewRepository.save(review);
        return ReviewResponseDto.toDto(savedReview);
    }

    @Transactional
    public ReviewResponseDto addOwnerReply(Long reviewId, String replyContent) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + reviewId));

        // 권한 검사: 현재 사용자가 해당 리뷰의 레스토랑을 소유하고 있는지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentKakaoId = authentication.getName();

        boolean isAuthorized = restaurantRepository.existsByKakaoIdAndRestaurantId(currentKakaoId, review.getRestaurant().getId());

        if (!isAuthorized) {
            throw new SecurityException("You are not authorized to reply to this review.");
        }

        review.updateOwnerReply(replyContent);
        return ReviewResponseDto.toDto(review);
    }

    public List<Review> findReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
}