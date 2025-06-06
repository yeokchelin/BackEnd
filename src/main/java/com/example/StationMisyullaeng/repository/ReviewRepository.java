package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Review;
import com.example.StationMisyullaeng.entity.Restaurant; // ★★★ Restaurant 엔티티 임포트 추가 ★★★
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 특정 Restaurant의 PK(id)를 기반으로 리뷰 목록을 찾습니다.
    List<Review> findByRestaurant_Id(Long restaurantId);

    // 특정 Store의 PK(storeId)를 기반으로 리뷰 목록을 찾습니다.
    List<Review> findByRestaurant_Store_StoreId(Long storeId);

    // ★★★ 특정 Restaurant에 연결된 모든 Review 삭제 메서드 추가 ★★★
    // @Transactional 어노테이션은 서비스 계층에서 처리하므로 여기에 필요 없습니다.
    void deleteByRestaurant(Restaurant restaurant);
}