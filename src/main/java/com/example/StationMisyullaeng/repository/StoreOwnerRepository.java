package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Long> {
    boolean existsByKakaoId(String kakaoId);
}

