package com.example.StationMisyullaeng.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponseDto {
    private Long favoriteId;
    private Long userId;
    private Long storeId;
    private String storeName;
}
