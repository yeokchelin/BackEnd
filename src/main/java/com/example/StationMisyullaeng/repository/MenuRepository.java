package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>{
    List<Menu> findByStore_StoreId(Long storeId);   //store 별 메뉴 리스트 가져오기


}
