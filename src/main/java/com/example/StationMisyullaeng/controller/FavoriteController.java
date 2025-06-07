package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    // [1] 찜 여부 확인
    @GetMapping("/users/{userId}/favorites/restaurants/{restaurantId}")
    public Map<String, Boolean> isFavorite(
            @PathVariable Long userId,
            @PathVariable Long restaurantId) {
        boolean isFavorite = favoriteService.isFavorite(userId, restaurantId);
        return Collections.singletonMap("isFavorite", isFavorite);
    }

    // [2] 찜 추가
    @PostMapping("/users/{userId}/favorites/restaurants/{restaurantId}")
    public ResponseEntity<?> addFavorite(
            @PathVariable Long userId,
            @PathVariable Long restaurantId) {
        try {
            favoriteService.addFavorite(userId, restaurantId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        }
    }

    // [3] 찜 삭제
    @DeleteMapping("/users/{userId}/favorites/restaurants/{restaurantId}")
    public ResponseEntity<?> removeFavorite(
            @PathVariable Long userId,
            @PathVariable Long restaurantId) {
        try {
            favoriteService.removeFavorite(userId, restaurantId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    // [4] 해당 식당 찜 개수
    @GetMapping("/restaurants/{restaurantId}/favorites/count")
    public Map<String, Long> getFavoriteCount(
            @PathVariable Long restaurantId) {
        long count = favoriteService.getFavoriteCount(restaurantId);
        return Map.of("count", count);
    }

    // [5] 특정 사용자의 찜한 가게 목록 반환
    @GetMapping("/users/{userId}/favorites/restaurants")
    public List<Restaurant> getFavoriteRestaurants(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }

}