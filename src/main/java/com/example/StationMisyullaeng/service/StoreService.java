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
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "모든 가게가 존재하지 않습니다. 가게를 등록해주세요. ");
        }
        return allStores;
    }

    //특정 가게 조회(상호명으로 가게 조회)
    public List<Store> getStoreByName(String name){
        List<Store> stores = storeRepository.findByNameContaining(name);
        if(stores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "등록된 가게가 존재하지 않습니다. ");
        }
        return stores;
    }

    //가게 등록
    @Transactional
    public Store createStore(Store store){
        return storeRepository.save(store); //레포지토리를 이용해서 저장하기
    }

    //가게 정보 수정
    @Transactional
    public Store updateStore(Long storeId, Store updatedStore) {
        //우선 수정할 가게가 존재하는지 조회, 없으면 예외 뿌리기
        Store existStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "수정할 가게가 존재하지 않습니다. "
                ));

        //각 필드가 null이 아닐 때만 업데이트 하기, null일경우 기존 정보 유지
        //상호명(name)에 값이 들어왔을 때만 업데이트
        if(updatedStore.getName() != null) {
            existStore.setName(updatedStore.getName());
        }

        //주소(address)에 값이 들어왔을 때만 업데이트
        if(updatedStore.getAddress() != null) {
            existStore.setAddress(updatedStore.getAddress());
        }

        //영업시간(hours)에 값이 들어왔을 때만 업데이트
        if(updatedStore.getHours() != null) {
            existStore.setHours(updatedStore.getHours());
        }

        //전화번호(phone)에 값이 들어왔을 때만 업데이트
        if(updatedStore.getPhone() != null) {
            existStore.setPhone(updatedStore.getPhone());
        }

        //가게 소개(description)에 값이 들어왔을 때만 업데이트
        if(updatedStore.getDescription() != null){
            existStore.setDescription(updatedStore.getDescription());
        }

        //가게 정보 업데이트 다 하면 업데이트 한 인스턴스 저장하기
        return storeRepository.save(existStore);


    }


}
