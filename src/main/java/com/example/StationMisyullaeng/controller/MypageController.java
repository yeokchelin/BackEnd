package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.FavoriteRequestDto;
import com.example.StationMisyullaeng.dto.FavoriteResponseDto;
import com.example.StationMisyullaeng.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final FavoriteService favoriteService;

    //즐겨찾기 추가
    @PostMapping("/favorite")
    public ResponseEntity<FavoriteResponseDto> createFavorite(@RequestBody FavoriteRequestDto requestDto) {
        FavoriteResponseDto response = favoriteService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //즐겨찾기 목록 모두보기
    @GetMapping("/favorite")
    public ResponseEntity<List<FavoriteResponseDto>> getAllFavorites() {
        return ResponseEntity.ok(favoriteService.findAll());
    }

    //각 유저 별 즐겨찾기 한 목록 조회
    @GetMapping("/favorite/user/{userId}")
    public ResponseEntity<List<FavoriteResponseDto>> getFavoritesByUser(@PathVariable Long userId) {
        List<FavoriteResponseDto> favorites = favoriteService.getFavoritesByUser(userId);
        return ResponseEntity.ok(favorites);
    }

    //가게 별 즐겨찾기 조회
    @GetMapping("/favorite/store/{storeId}")
    public ResponseEntity<List<FavoriteResponseDto>> getFavoritesByStore(@PathVariable Long storeId) {
        List<FavoriteResponseDto> favorites = favoriteService.getFavoritesByStore(storeId);
        return ResponseEntity.ok(favorites);
    }



    //즐겨찾기 삭제 //favorite 테이블의 기본키를 넣어야 삭제 완료
    @DeleteMapping("/favorite/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}