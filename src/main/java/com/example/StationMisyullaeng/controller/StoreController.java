package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    //모든 가게 조회하기
    @GetMapping
    public List<Store> getAllStore() {
        return storeService.getAllStore();
    }

    //이름으로 가게 조회
    @GetMapping("name/{name}")
    public List<Store> getStoreByName(@PathVariable String name){
        return storeService.getStoreByName(name);
    }

    //카테고리로 가게 조회(ex: 이자카야, 포장마차, 요리주점 등등)
    @GetMapping("category/{category}")
    public List<Store> getStoreByCategory(@PathVariable String category) {
        return storeService.getStoreByCategory(category);
    }

    @PostMapping    //가게 등록
    public Store createStore(@RequestBody Store store) {
        return storeService.createStore(store);
    }


}
