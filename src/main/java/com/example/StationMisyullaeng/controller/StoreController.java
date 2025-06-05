package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.entity.UserGrade;
import com.example.StationMisyullaeng.service.KakaoUserService;
import com.example.StationMisyullaeng.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // ★★★ CORS 허용을 위해 추가 (프론트엔드 URL에 맞게) ★★★
public class StoreController {

    private final StoreService storeService;
    private final KakaoUserService kakaoUserService;

    // 모든 가게 조회
    @GetMapping
    public List<Store> getAllStore() {
        return storeService.getAllStore();
    }

    // 상호명으로 가게 조회
    @GetMapping("name/{name}")
    public List<Store> getStoreByName(@PathVariable String name) {
        return storeService.getStoreByName(name);
    }

    // 가게 등록 (kakaoId 포함)
    @PostMapping
    public Store createStore(@RequestBody Store store) {
        Store createdStore = storeService.createStore(store);

        // 사용자 등급을 점주로 변경
        if (store.getKakaoId() != null) {
            kakaoUserService.updateUserGrade(store.getKakaoId(), UserGrade.OWNER);
        }

        return createdStore;
    }

    // 가게 정보 수정
    @PatchMapping("/{storeId}") // PATCH 매핑은 부분 업데이트에 더 적합합니다.
    public Store updateStore(@PathVariable Long storeId, @RequestBody Store store) {
        return storeService.updateStore(storeId, store);
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
    }

    // 사용자(kakaoId)가 등록한 가게 목록 조회
    @GetMapping("/user/{kakaoId}")
    public List<Store> getStoreByUser(@PathVariable String kakaoId) {
        return storeService.getStoresByKakaoId(kakaoId);
    }

}