package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.entity.Comment;
import com.example.StationMisyullaeng.entity.PostWrite;
import com.example.StationMisyullaeng.repository.CommentRepository;
import com.example.StationMisyullaeng.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment addComment(Long postId, Comment comment) {
        PostWrite post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (comment.getWriter() == null || comment.getWriter().isBlank()) {
            comment.setWriter("익명");
        }

        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long postId) {
        PostWrite post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return commentRepository.findByPostOrderByIdDesc(post);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment updateComment(Long commentId, Comment updated) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        comment.setContent(updated.getContent());
        // (필요하면 updatedAt 갱신)
        return commentRepository.save(comment);
    }

}

