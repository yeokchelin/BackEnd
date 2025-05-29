package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.PostWrite;
import com.example.StationMisyullaeng.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostWrite createPost(@RequestBody PostWrite post) {
        return postService.createPost(post);
    }
}