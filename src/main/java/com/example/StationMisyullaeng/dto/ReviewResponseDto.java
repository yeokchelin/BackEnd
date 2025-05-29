package com.example.StationMisyullaeng.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDto {

    private Long reviewId;
    private String title;
    private String content;
    private int rate;
    private String imagePath;

    // Store 정보 중 필요한 것만
    private Long storeId;
    private String storeName;
}
