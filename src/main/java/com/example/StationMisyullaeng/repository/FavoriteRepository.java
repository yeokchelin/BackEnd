package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Favorite;
import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    List<Favorite> findByStore(Store store);
}
