package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.entity.MateFoodPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateFoodPostCommentRepository extends JpaRepository<MateFoodPostComment, Long> {
    List<MateFoodPostComment> findByMateFoodPost(MateFoodPost post);
}