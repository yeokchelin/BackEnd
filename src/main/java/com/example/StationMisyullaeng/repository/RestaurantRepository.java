package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.entity.Store; // ★★★ Store 엔티티 임포트 추가 ★★★
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // findByStore가 Optional을 반환할 경우를 대비하여 추가 (필요시)

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByStationName(String stationName);

    List<Restaurant> findTop10ByOrderByRatingDesc();

    // kakaoId로 Store를 찾고, 해당 Store에 속한 Restaurant의 restaurantId가 주어진 restaurantId와 일치하는지 확인
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Restaurant r JOIN r.store s WHERE s.kakaoId = :kakaoId AND r.id = :restaurantId")
    boolean existsByKakaoIdAndRestaurantId(@Param("kakaoId") String kakaoId, @Param("restaurantId") Long restaurantId);

    // ★★★ 특정 Store에 연결된 모든 Restaurant 조회 메서드 추가 ★★★
    List<Restaurant> findByStore(Store store);

    // 단일 Restaurant을 반환하는 경우 (1:1 관계를 가정할 때)
    Optional<Restaurant> findOptionalByStore(Store store); // 예를 들어, Store와 Restaurant가 1:1이라고 가정할 때
}