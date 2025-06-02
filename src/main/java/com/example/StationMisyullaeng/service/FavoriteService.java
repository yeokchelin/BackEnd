package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.FavoriteRequestDto;
import com.example.StationMisyullaeng.dto.FavoriteResponseDto;
import com.example.StationMisyullaeng.entity.Favorite;
import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.entity.User;
import com.example.StationMisyullaeng.repository.FavoriteRepository;
import com.example.StationMisyullaeng.repository.StoreRepository;
import com.example.StationMisyullaeng.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 즐겨찾기 저장
    public FavoriteResponseDto save(FavoriteRequestDto requestDto) {

        //우선 즐겨찾기 하는 user가 존재하는지 findById()로 찾기 있으면 user 인스턴스 생성, 없으면 예외출력
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        //그리고 즐겨찾기 할 store가 존재하는지 findById()로 찾기 있으면 store 인스턴스에 저장, 없으면 예외 출력
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        //위에서 생성한 user, store 인스턴스를 @Builder를 사용해서 favorite 인스턴스에 값으로 저장
        Favorite favorite = Favorite.builder()
                .user(user)
                .store(store)
                .build();

        //저장하기
        Favorite saved = favoriteRepository.save(favorite);

        //DTO로 반환
        return convertToDto(saved);
    }

    //저장되어 있는 모든 값을 반환하는 메서드
    public List<FavoriteResponseDto> findAll() {
        List<Favorite> favorites = favoriteRepository.findAll();
        return favorites.stream()
                .map(this::convertToDto)
                .toList();
    }

    //유저 별 즐겨찾기 조회 메서드
    public List<FavoriteResponseDto> getFavoritesByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다: id=" + userId));

        List<Favorite> favoriteList = favoriteRepository.findByUser(user);

        return favoriteList.stream()
            .map(this::convertToDto)  // 또는 FavoriteMapper를 사용한다면 매퍼 클래스에서 변환
            .collect(Collectors.toList());
    }

    //가게 별 즐겨찾기 메서드
    public List<FavoriteResponseDto> getFavoritesByStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다: id=" + storeId));

        List<Favorite> favorites = favoriteRepository.findByStore(store);

        return favorites.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }


    //Favorite Entity Object => FavoriteResponseDto Object
    private FavoriteResponseDto convertToDto(Favorite favorite) {
        return FavoriteResponseDto.builder()
                .favoriteId(favorite.getFavoriteId())
                .userId(favorite.getUser().getUserId())
                .storeId(favorite.getStore().getStoreId())
                .storeName(favorite.getStore().getName())
                .build();
    }

    //즐겨찾기 삭제 기능
    public void deleteFavorite(Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기를 찾을 수 없습니다: id=" + favoriteId));
        favoriteRepository.delete(favorite);
    }

}
