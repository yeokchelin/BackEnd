package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.repository.TodayMenuRepository;
import com.example.StationMisyullaeng.entity.TodayMenu;
import com.example.StationMisyullaeng.service.TodayMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/today-menu")
@RequiredArgsConstructor
public class TodayMenuController {
    private final TodayMenuService todayMenuService;
    private final TodayMenuRepository todayMenuRepository;

    // "/api/today-menu/random?category=카테고리명"으로 맞춰줍니다!
    @GetMapping("/random")
    public TodayMenu getRandomMenuByCategory(@RequestParam String category) {
        List<TodayMenu> menus;
        if (category.equals("전체")) {
            menus = todayMenuRepository.findAll();
        } else {
            menus = todayMenuRepository.findByCategory(category);
        }
        if (menus.isEmpty()) throw new IllegalStateException("해당 카테고리의 메뉴가 없습니다.");
        Random random = new Random();
        return menus.get(random.nextInt(menus.size()));
    }
}