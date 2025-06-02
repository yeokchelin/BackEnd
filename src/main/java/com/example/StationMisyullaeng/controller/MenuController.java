package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.dto.MenuRequestDto;
import com.example.StationMisyullaeng.entity.Menu;
import com.example.StationMisyullaeng.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/menu")
public class MenuController {

    private final MenuService menuService;

    //가게 별 메뉴 조회 //성공
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Menu>> getMenusByStore(@PathVariable Long storeId) {
        List<Menu> menus = menuService.getMenusByStoreId(storeId);
        return ResponseEntity.ok(menus);
    }
    

    //메뉴 생성 //성공
    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody MenuRequestDto requestDto) {
        Menu menu = menuService.createMenu(requestDto);
        return ResponseEntity.ok(menu);
    }


    //메뉴 수정 //성공
    @PatchMapping("/{menuId}")
    public Menu partialUpdateMenu(@PathVariable Long menuId, @RequestBody MenuRequestDto dto) {
        return menuService.updateMenu(menuId, dto);
    }
    
    
}
