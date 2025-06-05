package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.Review;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDto {

    private Long reviewId;
    private String author;
    private String title;
    private String content;
    private Integer rate;
    private String imagePath;

    // Restaurant 정보
    private Long restaurantId;   // ⭐ 필드명 변경: storeId -> restaurantId
    private String restaurantName; // ⭐ 필드명 변경: storeName -> restaurantName

    private LocalDateTime createdAt;

    public static ReviewResponseDto toDto(Review review) {
        if (review == null) {
            return null;
        }

        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .author(review.getAuthor())
                .title(review.getTitle())
                .content(review.getContent())
                .rate(review.getRate())
                .imagePath(review.getImagePath())
                // ⭐ Review.getRestaurant()에서 Restaurant 엔티티의 id와 name 필드 가져오기
                .restaurantId(review.getRestaurant() != null ? review.getRestaurant().getId() : null)
                .restaurantName(review.getRestaurant() != null ? review.getRestaurant().getName() : null)
                .createdAt(review.getCreatedAt())
                .build();
    }
}
