package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Store;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    //select * from store where name like '%name%'
    List<Store> findByNameContaining(String name);
    List<Store> findByKakaoId(String kakaoId);


}