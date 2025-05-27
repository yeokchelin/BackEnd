package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public Store createStore(Store store) {
        return storeService.createStore(store);
    }


}
