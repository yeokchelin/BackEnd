package com.example.StationMisyullaeng.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteRequestDto {
    private Long userId;
    private Long storeId;
}
