package com.example.StationMisyullaeng.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {
    private String title;
    private String content;
    private int rate;
    private Long storeId;
}
