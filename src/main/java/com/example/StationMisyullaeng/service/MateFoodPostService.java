package com.example.StationMisyullaeng.service;


import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.repository.MateFoodPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MateFoodPostService {

    private final MateFoodPostRepository mateFoodPostRepository;

    public MateFoodPost createPost(MateFoodPost mateFoodPost) {
        return mateFoodPostRepository.save(mateFoodPost);
    }
}
