package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.Review;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReviewResponseDto {

    private Long reviewId;
    private String author;
    private String title;
    private String content;
    private Integer rate;
    private String imagePath;

    // Restaurant 정보
    private Long restaurantId;
    private String restaurantName;

    private LocalDateTime createdAt;

    // 점주 답글 필드
    private String ownerReplyContent;
    private LocalDateTime ownerRepliedAt;

    // ★★★ 점주 Kakao ID 필드 추가 (권한 확인용) ★★★
    private String storeOwnerKakaoId; // 리뷰가 달린 가게의 점주 Kakao ID

    public static ReviewResponseDto toDto(Review review) {
        if (review == null) {
            return null;
        }

        // Restaurant 및 Store 객체 null 체크
        Long resId = null;
        String resName = null;
        String storeOwnerId = null;

        if (review.getRestaurant() != null) {
            resId = review.getRestaurant().getId();
            resName = review.getRestaurant().getName();
            if (review.getRestaurant().getStore() != null) {
                storeOwnerId = review.getRestaurant().getStore().getKakaoId(); // Store 엔티티의 getKakaoId() 호출
            }
        }

        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .author(review.getAuthor())
                .title(review.getTitle())
                .content(review.getContent())
                .rate(review.getRate())
                .imagePath(review.getImagePath())
                .restaurantId(resId)
                .restaurantName(resName)
                .createdAt(review.getCreatedAt())
                .ownerReplyContent(review.getOwnerReplyContent())
                .ownerRepliedAt(review.getOwnerRepliedAt())
                .storeOwnerKakaoId(storeOwnerId) // ★★★ storeOwnerKakaoId 매핑 ★★★
                .build();
    }
}