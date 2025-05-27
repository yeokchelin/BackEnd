package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Store createStore(Store store){
        return storeRepository.save(store);
    }


}
