package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String stationName;
    private String category;
    private Double rating;
    private Integer reviewCount;
    private String imageUrl;
    private String description;

    // Store 정보 포함 (Restaurant 엔티티가 Store를 참조하므로)
    private Long storeId;
    private String storeName; // Store의 name (상호명)
    private String storeKakaoId; // Store의 kakaoId (점주 식별용)

    public static RestaurantResponseDto toDto(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }

        // 지연 로딩(Lazy Loading) 문제를 피하기 위해,
        // restaurant.getStore()가 null이 아닐 때만 Store 정보에 접근합니다.
        Long associatedStoreId = null;
        String associatedStoreName = null;
        String associatedStoreKakaoId = null;

        if (restaurant.getStore() != null) { // Store 객체가 초기화되었는지 확인
            associatedStoreId = restaurant.getStore().getStoreId();
            associatedStoreName = restaurant.getStore().getName();
            associatedStoreKakaoId = restaurant.getStore().getKakaoId();
        }

        return RestaurantResponseDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .stationName(restaurant.getStationName())
                .category(restaurant.getCategory())
                .rating(restaurant.getRating())
                .reviewCount(restaurant.getReviewCount())
                .imageUrl(restaurant.getImageUrl())
                .description(restaurant.getDescription())
                .storeId(associatedStoreId)
                .storeName(associatedStoreName)
                .storeKakaoId(associatedStoreKakaoId)
                .build();
    }
}