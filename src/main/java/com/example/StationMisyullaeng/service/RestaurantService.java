package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.Category;
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

    // 카테고리별로 맛집 리스트 반환하기
    public List<Restaurant> getRestaurantsByCategory(Category category) {
        return restaurantRepository.findByCategory(category);
    }

    //지하철역 이름과 카테고리 별로 맛집 리스트 반환하기
    public List<Restaurant> getRestaurantsByStationAndCategory(String stationName, Category category){
        return restaurantRepository.findByStationNameAndCategory(stationName, category);
    }


}


