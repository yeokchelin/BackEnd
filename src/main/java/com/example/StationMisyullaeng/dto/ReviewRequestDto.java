package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.entity.Review;
import com.example.StationMisyullaeng.entity.Restaurant;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {
    private Long userId;
    private String author;
    private String title;
    private String content;
    private Integer rate;
    private Long restaurantId;

    // ★★★ 점주 답글 작성을 위한 필드 추가 (필요시) ★★★
    // 이 DTO는 기본적으로 리뷰 '작성'용이지만, 답글 '수정/작성' API에서 재활용할 수도 있습니다.
    // 만약 답글만 따로 전송한다면 이 필드는 필요 없을 수 있습니다.
    // 여기서는 답글 API 요청 시 'content' 필드를 답글 내용으로 재활용하거나,
    // 별도의 ReplyRequestDto를 만들고, ReviewRequestDto는 그대로 두는 것이 더 일반적입니다.
    // 일단은 Review 엔티티에 추가된 ownerReplyContent를 위해 DTO에 추가하지 않고,
    // 답글 API는 별도의 DTO 또는 직접 문자열을 받는 방식으로 구현을 고려합니다.
    // => 이 DTO는 리뷰 작성용으로 유지하고, 답글은 PATCH 등으로 Review ID와 content만 받도록 하겠습니다.

    public Review toEntity(Restaurant restaurant, KakaoUser user) {
        return Review.builder()
                .author(this.author)
                .title(this.title)
                .content(this.content)
                .rate(this.rate)
                .restaurant(restaurant)
                .user(user)
                .build();
    }
}