package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // 🔍 지하철역 기준 맛집 리스트 반환
    public List<Restaurant> getRestaurantsByStation(String stationName) {
        return restaurantRepository.findByStationName(stationName);
    }

    // ➕ 새로운 맛집 저장
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    // 🔝 평점 높은 맛집 10개 반환
    public List<Restaurant> getTopRatedRestaurants() {
        return restaurantRepository.findTop10ByOrderByRatingDesc();
    }

    // 역 이름으로 정보 가져오기
    public List<String> getRestaurantNamesByStationName(String stationName) {
        return restaurantRepository.findByStationName(stationName)
                .stream()
                .map(Restaurant::getName)
                .toList();
    }

    // 역 이름 클릭시 상세정보 제공
    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("음식점 정보를 찾을 수 없습니다."));
    }


}


