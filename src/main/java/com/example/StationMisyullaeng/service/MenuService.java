package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.MenuRequestDto;
import com.example.StationMisyullaeng.entity.Menu;
import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.repository.MenuRepository;
import com.example.StationMisyullaeng.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    //가게 별 메뉴 조회
    public List<Menu> getMenusByStoreId(Long storeId) {
        return menuRepository.findByStore_StoreId(storeId);
    }


    //메뉴 생성
    @Transactional
    public Menu createMenu(MenuRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        Menu menu = Menu.builder()
                .menuName(requestDto.getMenuName())
                .price(requestDto.getPrice())
                .menuDescription(requestDto.getMenuDescription())
                .imgUrl(requestDto.getImgUrl())
                .store(store)
                .build();

        return menuRepository.save(menu);
    }

    //메뉴 수정
    @Transactional
    public Menu updateMenu(Long menuId, MenuRequestDto dto) {
        //우선적으로 수정할 메뉴가 존재하는지 확인한 후
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다: " + menuId));

        //입력한 필드의 값만 수정, 입력 필드가 null일경우 유지
        if (dto.getMenuName() != null) {
            menu.setMenuName(dto.getMenuName());
        }
        if (dto.getPrice() != null) {
            menu.setPrice(dto.getPrice());
        }
        if (dto.getMenuDescription() != null) {
            menu.setMenuDescription(dto.getMenuDescription());
        }
        if (dto.getImgUrl() != null) {
            menu.setImgUrl(dto.getImgUrl());
        }

        return menuRepository.save(menu);
    }
    
}
