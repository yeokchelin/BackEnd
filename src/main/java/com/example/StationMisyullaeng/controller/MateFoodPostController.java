package com.example.StationMisyullaeng.controller;


import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.service.MateFoodPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mateFoodPost")
@RequiredArgsConstructor
public class MateFoodPostController {

    private final MateFoodPostService mateFoodPostService;

    // 🔸 글 등록
    @PostMapping
    public MateFoodPost createMateFoodPost(@RequestBody MateFoodPost mateFoodPost) {
        return mateFoodPostService.createPost(mateFoodPost);
    }

    // 🔸 전체 글 조회
    @GetMapping
    public List<MateFoodPost> getAllPosts() {
        return mateFoodPostService.getAllPosts();
    }

    // 🔸 ID로 특정 글 조회
    @GetMapping("/{id}")
    public MateFoodPost getPostById(@PathVariable Long id) {
        return mateFoodPostService.getPostById(id);
    }




}
