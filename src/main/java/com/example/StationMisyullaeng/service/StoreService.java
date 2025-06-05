package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.entity.UserGrade;
import com.example.StationMisyullaeng.repository.KakaoUserRepository;
import com.example.StationMisyullaeng.repository.RestaurantRepository;
import com.example.StationMisyullaeng.repository.ReviewRepository; // ReviewRepository 임포트 추가 (DataInitializer 위함)
import com.example.StationMisyullaeng.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional; // Optional 임포트 추가

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final KakaoUserRepository kakaoUserRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository; // ReviewRepository 주입 추가 (DataInitializer에서 사용)

    // 모든 가게 조회
    public List<Store> getAllStore(){
        List<Store> allStores = storeRepository.findAll();
        if(allStores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "모든 가게가 존재하지 않습니다. 가게를 등록해주세요. ");
        }
        return allStores;
    }

    // 특정 가게 조회(상호명으로 가게 조회)
    public List<Store> getStoreByName(String name){
        List<Store> stores = storeRepository.findByNameContaining(name);
        if(stores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "등록된 가게가 존재하지 않습니다. ");
        }
        return stores;
    }

    // 가게 등록 및 Restaurant 자동 생성
    @Transactional
    public Store createStore(Store store){
        Store createdStore = storeRepository.save(store);

        // 해당 Store에 연결될 Restaurant 정보 생성 및 저장
        Restaurant newRestaurant = Restaurant.builder()
                .name(createdStore.getName()) // 가게 이름과 동일
                .stationName(createdStore.getMeetingStation()) // Store의 meetingStation을 Restaurant의 stationName으로
                .category(createdStore.getCategory()) // Store의 category와 동일
                .rating(0.0) // 초기 평점 0
                .reviewCount(0) // 초기 리뷰 수 0
                .imageUrl(createdStore.getImageUrl()) // Store의 imageUrl과 동일
                .description(createdStore.getDescription()) // Store의 description과 동일
                .store(createdStore) // 생성된 Store 엔티티와 연결
                .build();

        restaurantRepository.save(newRestaurant); // Restaurant 정보 저장

        return createdStore; // 생성된 Store 반환
    }

    // 가게 정보 수정
    @Transactional
    public Store updateStore(Long storeId, Store updatedStore) {
        Store existStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "수정할 가게가 존재하지 않습니다. "
                ));

        // 각 필드가 null이 아닐 때만 업데이트
        if(updatedStore.getName() != null) {
            existStore.setName(updatedStore.getName());
        }
        if(updatedStore.getAddress() != null) {
            existStore.setAddress(updatedStore.getAddress());
        }
        if(updatedStore.getHours() != null) {
            existStore.setHours(updatedStore.getHours());
        }
        if(updatedStore.getContact() != null) {
            existStore.setContact(updatedStore.getContact());
        }
        if(updatedStore.getDescription() != null){
            existStore.setDescription(updatedStore.getDescription());
        }
        if(updatedStore.getCategory() != null) {
            existStore.setCategory(updatedStore.getCategory());
        }
        if(updatedStore.getImageUrl() != null) {
            existStore.setImageUrl(updatedStore.getImageUrl());
        }
        if(updatedStore.getRegistrationNumber() != null) {
            existStore.setRegistrationNumber(updatedStore.getRegistrationNumber());
        }
        if(updatedStore.getMeetingStation() != null) {
            existStore.setMeetingStation(updatedStore.getMeetingStation());
        }

        // ★★★ Restaurant 정보도 Store 업데이트와 함께 업데이트 ★★★
        // findOptionalByStore를 사용하여 Optional<Restaurant>를 반환받고 ifPresent 사용
        restaurantRepository.findOptionalByStore(existStore)
                .ifPresent(restaurant -> {
                    if(updatedStore.getName() != null) restaurant.setName(updatedStore.getName());
                    if(updatedStore.getMeetingStation() != null) restaurant.setStationName(updatedStore.getMeetingStation());
                    if(updatedStore.getCategory() != null) restaurant.setCategory(updatedStore.getCategory());
                    if(updatedStore.getImageUrl() != null) restaurant.setImageUrl(updatedStore.getImageUrl());
                    if(updatedStore.getDescription() != null) restaurant.setDescription(updatedStore.getDescription());
                    // rating과 reviewCount는 리뷰 시스템에 의해 관리되므로 여기서 직접 업데이트하지 않습니다.
                    restaurantRepository.save(restaurant); // 변경 사항 저장
                });

        return storeRepository.save(existStore);
    }

    // 가게 삭제 (Restaurant도 함께 삭제)
    @Transactional
    public void deleteStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "삭제할 가게가 존재하지 않습니다."
                ));

        // 연결된 Restaurant도 함께 삭제 (CascadeType.ALL 설정이 없다면 수동으로 삭제)
        // Review와 Restaurant 간의 외래 키 제약 조건으로 인해 Review를 먼저 삭제해야 할 수 있습니다.
        // RestaurantService의 deleteRestaurant 메서드가 있다면 그것을 호출하는 것이 더 안전합니다.
        // 여기서는 Store에 연결된 모든 Restaurant를 찾아서 삭제합니다.
        restaurantRepository.findByStore(store)
                .forEach(restaurant -> {
                    // 해당 Restaurant에 연결된 모든 Review를 먼저 삭제
                    reviewRepository.deleteByRestaurant(restaurant); // ReviewRepository에 deleteByRestaurant 메서드 필요
                    restaurantRepository.delete(restaurant); // Restaurant 삭제
                });

        String kakaoId = store.getKakaoId();
        storeRepository.delete(store); // Store 삭제

        List<Store> remainingStores = storeRepository.findByKakaoId(kakaoId);
        if (remainingStores.isEmpty()) {
            KakaoUser user = kakaoUserRepository.findByKakaoId(kakaoId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "사용자를 찾을 수 없습니다."
                    ));
            user.setGrade(UserGrade.GENERAL);
        }
    }

    public List<Store> getStoresByKakaoId(String kakaoId) {
        return storeRepository.findByKakaoId(kakaoId);
    }

}