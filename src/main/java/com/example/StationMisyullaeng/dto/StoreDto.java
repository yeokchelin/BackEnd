package com.example.StationMisyullaeng.dto;

import com.example.StationMisyullaeng.entity.Store;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private Long id; // storeId를 이 필드로 변환!
    private String name;
    private String address;
    private String hours;
    private String phone;
    private String contact;
    private String description;
    private String registrationNumber;
    private String category;
    private String imageUrl;
    private String meetingStation;
    private String kakaoId;

    // Store 엔티티 -> StoreDto 변환 메서드
    public static StoreDto fromEntity(Store store) {
        if (store == null) return null;
        return StoreDto.builder()
                .id(store.getStoreId()) // 핵심: storeId → id
                .name(store.getName())
                .address(store.getAddress())
                .hours(store.getHours())
                .phone(store.getPhone())
                .contact(store.getContact())
                .description(store.getDescription())
                .registrationNumber(store.getRegistrationNumber())
                .category(store.getCategory())
                .imageUrl(store.getImageUrl())
                .meetingStation(store.getMeetingStation())
                .kakaoId(store.getKakaoId())
                .build();
    }
}

