package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

/*
TODO : 데이터베이스와 직접 통신하는 객체
TODO : Spring Data JPA가 자동으로 SQL을 생성해줌
TODO : Restaurant 엔티티와 연결된 테이블에 대한 CRUD(조회, 저장, 삭제 등) 기능을 제공
*/

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // 특정 지하철역 이름으로 맛집 목록 조회
    List<Restaurant> findByStationName(String stationName);

    // 평점 높은 순으로 Top 10 맛집 조회
    List<Restaurant> findTop10ByOrderByRatingDesc();

}