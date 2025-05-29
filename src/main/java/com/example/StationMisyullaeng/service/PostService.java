package com.example.StationMisyullaeng.service;



import com.example.StationMisyullaeng.entity.PostWrite;
import com.example.StationMisyullaeng.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostWrite createPost(PostWrite post) {
        return postRepository.save(post);
    }
}