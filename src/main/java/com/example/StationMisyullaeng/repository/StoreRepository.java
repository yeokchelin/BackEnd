package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Store;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    //select * from store where name like '%name%'
    List<Store> findByNameContaining(String name);

}