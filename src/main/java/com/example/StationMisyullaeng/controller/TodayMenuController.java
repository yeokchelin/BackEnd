package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.TodayMenu;
import com.example.StationMisyullaeng.service.TodayMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/today-menu")
@RequiredArgsConstructor
public class TodayMenuController {
    private final TodayMenuService todayMenuService;

    @GetMapping
    public TodayMenu getTodayMenu() {
        return todayMenuService.getTodayMenu();
    }
}


