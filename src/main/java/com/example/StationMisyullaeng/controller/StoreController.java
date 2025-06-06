package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.StoreDto;
import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.entity.UserGrade;
import com.example.StationMisyullaeng.service.KakaoUserService;
import com.example.StationMisyullaeng.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StoreController {

    private final StoreService storeService;
    private final KakaoUserService kakaoUserService;

    // 모든 가게 조회
    @GetMapping
    public List<StoreDto> getAllStore() {
        return storeService.getAllStore()
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 상호명으로 가게 조회
    @GetMapping("name/{name}")
    public List<StoreDto> getStoreByName(@PathVariable String name) {
        return storeService.getStoreByName(name)
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 가게 등록 (kakaoId 포함)
    @PostMapping
    public StoreDto createStore(@RequestBody Store store) {
        Store createdStore = storeService.createStore(store);

        // 사용자 등급을 점주로 변경
        if (store.getKakaoId() != null) {
            kakaoUserService.updateUserGrade(store.getKakaoId(), UserGrade.OWNER);
        }

        return StoreDto.fromEntity(createdStore); // StoreDto로 반환
    }

    // 가게 정보 수정
    @PatchMapping("/{storeId}")
    public StoreDto updateStore(@PathVariable Long storeId, @RequestBody Store store) {
        return StoreDto.fromEntity(storeService.updateStore(storeId, store));
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
    }

    // 사용자(kakaoId)가 등록한 가게 목록 조회
    @GetMapping("/user/{kakaoId}")
    public List<StoreDto> getStoreByUser(@PathVariable String kakaoId) {
        return storeService.getStoresByKakaoId(kakaoId)
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }
}

