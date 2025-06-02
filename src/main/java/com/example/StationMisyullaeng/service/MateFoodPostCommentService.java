package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.entity.MateFoodPostComment;
import com.example.StationMisyullaeng.repository.MateFoodPostCommentRepository;
import com.example.StationMisyullaeng.repository.MateFoodPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MateFoodPostCommentService {

    private final MateFoodPostRepository postRepository;
    private final MateFoodPostCommentRepository commentRepository;

    public MateFoodPostComment createComment(Long postId, String writer, String content) {
        MateFoodPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        MateFoodPostComment comment = MateFoodPostComment.builder()
                .writer(writer)
                .content(content)
                .mateFoodPost(post)
                .build();

        return commentRepository.save(comment);
    }

    public List<MateFoodPostComment> getCommentsByPostId(Long postId) {
        MateFoodPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return commentRepository.findByMateFoodPost(post);
    }
}
