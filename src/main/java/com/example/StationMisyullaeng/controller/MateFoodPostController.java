package com.example.StationMisyullaeng.controller;


import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.service.MateFoodPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mateFoodPost")
@RequiredArgsConstructor
public class MateFoodPostController {

    private final MateFoodPostService mateFoodPostService;

    @PostMapping
    public MateFoodPost createMateFoodPost(@RequestBody MateFoodPost mateFoodPost) {
        return mateFoodPostService.createPost(mateFoodPost);
    }

}
