package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Store;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}