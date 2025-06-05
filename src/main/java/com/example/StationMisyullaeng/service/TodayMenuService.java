package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.TodayMenu;
import com.example.StationMisyullaeng.repository.TodayMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TodayMenuService {
    private final TodayMenuRepository menuRepository;

    public TodayMenu getTodayMenu() {
        List<TodayMenu> allMenus = menuRepository.findAll();
        if (allMenus.isEmpty()) throw new IllegalStateException("등록된 메뉴가 없습니다.");

        // 오늘 날짜를 시드로 사용하여 매일 동일한 메뉴 추천
        int seed = LocalDate.now().toString().hashCode();
        Random random = new Random(seed);
        int idx = random.nextInt(allMenus.size());
        return allMenus.get(idx);
    }
}


