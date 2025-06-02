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

    //상호명으로 가게 조회
    @GetMapping("name/{name}")
    public List<Store> getStoreByName(@PathVariable String name){
        return storeService.getStoreByName(name);
    }


    @PostMapping    //가게 등록
    public Store createStore(@RequestBody Store store) {
        return storeService.createStore(store);
    }

    @PatchMapping("/{storeId}")
    public Store updateStore(@PathVariable Long storeId, @RequestBody Store store) {
        return storeService.updateStore(storeId, store);
    }


}
