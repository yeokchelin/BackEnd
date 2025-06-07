package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.Favorite;
import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.repository.FavoriteRepository;
import com.example.StationMisyullaeng.repository.KakaoUserRepository;
import com.example.StationMisyullaeng.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepo;
    private final KakaoUserRepository userRepo;
    private final RestaurantRepository restaurantRepo;

    @Transactional
    public boolean isFavorite(Long userId, Long restaurantId) {
        KakaoUser user = userRepo.findById(userId).orElseThrow();
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
        return favoriteRepo.existsByUserAndRestaurant(user, restaurant);
    }

    @Transactional
    public void addFavorite(Long userId, Long restaurantId) {
        KakaoUser user = userRepo.findById(userId).orElseThrow();
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
        if (favoriteRepo.existsByUserAndRestaurant(user, restaurant)) {
            throw new IllegalStateException("이미 찜한 식당입니다.");
        }
        favoriteRepo.save(Favorite.builder().user(user).restaurant(restaurant).build());
    }

    @Transactional
    public void removeFavorite(Long userId, Long restaurantId) {
        KakaoUser user = userRepo.findById(userId).orElseThrow();
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
        Favorite favorite = favoriteRepo.findByUserAndRestaurant(user, restaurant)
                .orElseThrow(() -> new IllegalStateException("찜 내역이 없습니다."));
        favoriteRepo.delete(favorite);
    }

    @Transactional
    public long getFavoriteCount(Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
        return favoriteRepo.countByRestaurant(restaurant);
    }

    // 찜한 가게 목록 (userId로 조회)
    @Transactional
    public List<Restaurant> getFavoritesByUser(Long userId) {
        KakaoUser user = userRepo.findById(userId).orElseThrow();
        List<Favorite> favorites = favoriteRepo.findAllByUser(user);
        return favorites.stream()
                .map(Favorite::getRestaurant)
                .toList();
    }

}


