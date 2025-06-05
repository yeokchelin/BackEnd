package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.PostWrite;
import com.example.StationMisyullaeng.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freeboard")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostWrite createPost(@RequestBody PostWrite post) {
        return postService.createPost(post);
    }

    @GetMapping
    public List<PostWrite> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/{id}")
    public PostWrite updatePost(@PathVariable Long id, @RequestBody PostWrite updatedPost) {
        return postService.updatePost(id, updatedPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }


}