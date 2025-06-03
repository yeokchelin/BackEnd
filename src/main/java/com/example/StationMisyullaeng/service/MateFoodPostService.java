package com.example.StationMisyullaeng.service;


import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.repository.MateFoodPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MateFoodPostService {

    private final MateFoodPostRepository mateFoodPostRepository;

    public MateFoodPost createPost(MateFoodPost mateFoodPost) {
        return mateFoodPostRepository.save(mateFoodPost);
    }

    public MateFoodPost getPostById(Long id) {
        return mateFoodPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    public List<MateFoodPost> getAllPosts() {
        return mateFoodPostRepository.findAll();
    }
}
