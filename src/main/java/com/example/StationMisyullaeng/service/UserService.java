package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.User;
import com.example.StationMisyullaeng.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    //저장 된 user 확인용 메서드
    public List<User> showAllUsers(){
        return userRepository.findAll();
    }

    //user 저장 메서드
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
