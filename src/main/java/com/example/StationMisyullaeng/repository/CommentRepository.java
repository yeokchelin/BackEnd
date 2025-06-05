package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Comment;
import com.example.StationMisyullaeng.entity.PostWrite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByIdDesc(PostWrite post);
}
