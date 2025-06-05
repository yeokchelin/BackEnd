package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.TodayMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodayMenuRepository extends JpaRepository<TodayMenu, Long> {
    List<TodayMenu> findByCategory(String category);

}


