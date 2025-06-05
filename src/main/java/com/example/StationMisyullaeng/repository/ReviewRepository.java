package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // ⭐ 변경: 특정 Restaurant의 PK(id)를 기반으로 리뷰 목록을 찾습니다.
    // Review 엔티티의 필드 'restaurant' (타입 Restaurant)의 'id' 필드를 기준으로 검색합니다.
    List<Review> findByRestaurant_Id(Long restaurantId);
}
