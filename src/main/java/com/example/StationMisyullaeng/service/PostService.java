package com.example.StationMisyullaeng.service;



import com.example.StationMisyullaeng.entity.PostWrite;
import com.example.StationMisyullaeng.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostWrite createPost(PostWrite post) {
        return postRepository.save(post);
    }

    public List<PostWrite> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); // 최신순 정렬
    }

    public PostWrite updatePost(Long id, PostWrite updatedPost) {
        PostWrite existingPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setWriter(updatedPost.getWriter());

        return postRepository.save(existingPost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


}