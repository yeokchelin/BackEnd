package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.User;
import com.example.StationMisyullaeng.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //저장 된 모든 유저 확인
    @GetMapping
    public List<User> showAllUsers() {
        return userService.showAllUsers();
    }


    //kakao login api에서 넘겨받은 값 저장
    @PostMapping
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

}
