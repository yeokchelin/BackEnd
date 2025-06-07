package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Favorite;
import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserAndRestaurant(KakaoUser user, Restaurant restaurant);
    Optional<Favorite> findByUserAndRestaurant(KakaoUser user, Restaurant restaurant);
    long countByRestaurant(Restaurant restaurant);
    List<Favorite> findAllByUser(KakaoUser user);
}



