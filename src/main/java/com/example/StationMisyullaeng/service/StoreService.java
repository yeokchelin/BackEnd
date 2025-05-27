package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;


    //모든 가게 조회
    public List<Store> getAllStore(){
        List<Store> allStores = storeRepository.findAll();
        if(allStores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "모든 가게가 존재하지 않습니다. 가게를 등록해주세요. ");
        }
        return allStores;
    }

    //특정 가게 조회(가게 이름으로 찾기)
    public List<Store> getStoreByName(String name){
        List<Store> stores = storeRepository.findByNameContaining(name);
        if(stores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "등록된 가게가 존재하지 않습니다. ");
        }
        return stores;
    }

    //가게 카테고리로 조회하기(ex: 이자카야, 포장마차, 요리주점 등등)
    public List<Store> getStoreByCategory(String category) {
        List<Store> stores = storeRepository.findByCategoryContaining(category);
        if(stores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    category + "에 해당하는 카테고리의 가게가 존재하지 않습니다. ");
        }
        return stores;
    }

    //가게 등록
    @Transactional
    public Store createStore(Store store){
        return storeRepository.save(store); //레포지토리를 이용해서 저장하기
    }


}
