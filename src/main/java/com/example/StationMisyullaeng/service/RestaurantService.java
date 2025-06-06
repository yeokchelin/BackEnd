package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.dto.RestaurantResponseDto; // ★★★ RestaurantResponseDto 임포트 추가 ★★★
import com.example.StationMisyullaeng.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 임포트 추가 (조회 메서드에 사용)

import java.util.List;
import java.util.stream.Collectors; // Collectors 임포트 추가

/*
TODO : 비즈니스 로직을 처리하는 계층
TODO : 컨트롤러가 요청을 받으면, 실제 처리(데이터 조회/저장 등)는 서비스
TODO : DB 접근은 Repository에 위임하고, 그 중간 다리 역할
*/


@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    // 🔍 전체 맛집 리스트 반환
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public List<RestaurantResponseDto> getAllRestaurants() { // ★★★ 반환 타입 변경 ★★★
        return restaurantRepository.findAll().stream()
                .map(RestaurantResponseDto::toDto) // ★★★ 엔티티를 DTO로 변환 ★★★
                .collect(Collectors.toList());
    }

    // 🔍 지하철역 기준 맛집 리스트 반환
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public List<RestaurantResponseDto> getRestaurantsByStation(String stationName) { // ★★★ 반환 타입 변경 ★★★
        return restaurantRepository.findByStationName(stationName).stream()
                .map(RestaurantResponseDto::toDto) // ★★★ 엔티티를 DTO로 변환 ★★★
                .collect(Collectors.toList());
    }

    // ➕ 새로운 맛집 저장 (이 메서드는 엔티티를 저장하는 역할이므로, DTO로의 변환은 컨트롤러에서 처리)
    @Transactional // 저장 작업이므로 트랜잭션 필요
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    // 🔝 평점 높은 맛집 10개 반환
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public List<RestaurantResponseDto> getTopRatedRestaurants() { // ★★★ 반환 타입 변경 ★★★
        return restaurantRepository.findTop10ByOrderByRatingDesc().stream()
                .map(RestaurantResponseDto::toDto) // ★★★ 엔티티를 DTO로 변환 ★★★
                .collect(Collectors.toList());
    }

    // 역 이름으로 정보 가져오기 (List<String> 반환이므로 변경 없음)
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public List<String> getRestaurantNamesByStationName(String stationName) {
        return restaurantRepository.findByStationName(stationName)
                .stream()
                .map(Restaurant::getName)
                .toList();
    }

    // 역 이름 클릭시 상세정보 제공
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public RestaurantResponseDto getById(Long id) { // ★★★ 반환 타입 변경 ★★★
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("음식점 정보를 찾을 수 없습니다."));
        return RestaurantResponseDto.toDto(restaurant); // ★★★ 엔티티를 DTO로 변환 ★★★
    }

    // 추가 메서드 (필요시)
    // 현재 로그인한 사용자(점주)가 해당 Restaurant을 소유하고 있는지 확인하는 메서드
    // public boolean isOwnerOfRestaurant(String kakaoId, Long restaurantId) {
    //     return restaurantRepository.existsByKakaoIdAndRestaurantId(kakaoId, restaurantId);
    // }
}