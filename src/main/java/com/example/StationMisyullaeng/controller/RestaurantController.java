package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.StationRequestDto;
// import com.example.StationMisyullaeng.entity.Restaurant; // ★★★ Restaurant 엔티티 임포트 제거 (직접 반환하지 않으므로) ★★★
import com.example.StationMisyullaeng.dto.RestaurantResponseDto; // ★★★ RestaurantResponseDto 임포트 추가 ★★★
import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // HttpStatus 임포트 추가 (createRestaurant 응답용)
import org.springframework.http.ResponseEntity; // ResponseEntity 임포트 추가
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
    public List<RestaurantResponseDto> getAllRestaurants() { // ★★★ 반환 타입 변경 ★★★
        return restaurantService.getAllRestaurants();
    }

    // 🔍 특정 지하철역 이름으로 맛집 조회
    @GetMapping("/station/{stationName}")
    public List<RestaurantResponseDto> getByStation(@PathVariable String stationName) { // ★★★ 반환 타입 변경 ★★★
        return restaurantService.getRestaurantsByStation(stationName);
    }

    // ➕ 새로운 맛집 등록
    @PostMapping
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@RequestBody Restaurant restaurant) { // ★★★ @RequestBody는 Restaurant 엔티티 그대로 받음 ★★★
        Restaurant savedRestaurant = restaurantService.save(restaurant); // 서비스는 엔티티를 반환
        return new ResponseEntity<>(RestaurantResponseDto.toDto(savedRestaurant), HttpStatus.CREATED); // ★★★ 저장된 엔티티를 DTO로 변환하여 반환 ★★★
    }

    // 🔝 평점 높은 Top 10 맛집
    @GetMapping("/top-rated")
    public List<RestaurantResponseDto> getTopRated() { // ★★★ 반환 타입 변경 ★★★
        return restaurantService.getTopRatedRestaurants();
    }

    // 지하철 역 이름 리스트 반환 (StationRequestDto 사용)
    @PostMapping("/by-station") // ★★★ POST 매핑에 유의 (StationRequestDto를 @RequestBody로 받기 위함) ★★★
    public List<RestaurantResponseDto> getRestaurantsByStation(@RequestBody StationRequestDto request) { // ★★★ 반환 타입 변경 ★★★
        return restaurantService.getRestaurantsByStation(request.getStationName());
    }

    // 🔍 음식점 ID로 상세 정보 조회
    @GetMapping("/{id}")
    public RestaurantResponseDto getRestaurantById(@PathVariable Long id) { // ★★★ 반환 타입 변경 ★★★
        return restaurantService.getById(id);
    }
}