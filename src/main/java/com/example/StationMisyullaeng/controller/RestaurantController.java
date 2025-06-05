package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.StationRequestDto;
import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
TODO : 메인페이지 관련 내용
TODO : 외부 요청을 받는 진입 지점이야 (API 요청을 처리)
TODO : 프론트엔드(React)에서 /api/restaurants 등으로 HTTP 요청을 보내면, 그걸 받아서 Service에 전달하고 결과를 응답해 줘
*/


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 🔍 전체 맛집 리스트 반환
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    // 🔍 특정 지하철역 이름으로 맛집 조회
    @GetMapping("/station/{stationName}")
    public List<Restaurant> getByStation(@PathVariable String stationName) {
        return restaurantService.getRestaurantsByStation(stationName);
    }

    // ➕ 새로운 맛집 등록
    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    // 🔝 평점 높은 Top 10 맛집
    @GetMapping("/top-rated")
    public List<Restaurant> getTopRated() {
        return restaurantService.getTopRatedRestaurants();
    }

    @PostMapping("/by-station")
    public List<Restaurant> getRestaurantsByStation(@RequestBody StationRequestDto request) {
        return restaurantService.getRestaurantsByStation(request.getStationName());
    }
    
    // 🔍 음식점 ID로 상세 정보 조회
    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getById(id);
    }

}