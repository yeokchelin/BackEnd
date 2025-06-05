package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.Review;
import com.example.StationMisyullaeng.entity.Restaurant; // ⭐ Restaurant 엔티티 임포트
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {
    private String author;
    private String title;
    private String content;
    private Integer rate;
    private Long restaurantId; // ⭐ 필드명 변경: storeId -> restaurantId (프론트엔드에서 restaurant.id를 보냄)

    // ReviewService에서 ReviewRequestDto의 Long restaurantId를 바탕으로 Restaurant 엔티티를 찾은 후,
    // 이 메서드를 호출하여 Review 엔티티를 생성합니다.
    public Review toEntity(Restaurant restaurant) { // ⭐ 파라미터 타입 변경: Store -> Restaurant
        return Review.builder()
                .author(this.author)
                .title(this.title)
                .content(this.content)
                .rate(this.rate)
                .restaurant(restaurant) // ⭐ Review 엔티티의 'restaurant' 필드에 매핑
                .build();
    }
}
