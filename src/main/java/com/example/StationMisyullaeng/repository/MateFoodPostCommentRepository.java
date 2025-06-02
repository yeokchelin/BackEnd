// src/main/java/com/example/StationMisyullaeng/repository/MateFoodPostCommentRepository.java
package com.example.StationMisyullaeng.repository; // 실제 프로젝트 패키지 경로로 수정하세요.

import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.entity.MateFoodPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateFoodPostCommentRepository extends JpaRepository<MateFoodPostComment, Long> {

    // 특정 MateFoodPost에 속한 댓글들을 createdAt 기준으로 내림차순 정렬하여 조회
    List<MateFoodPostComment> findByMateFoodPostOrderByCreatedAtDesc(MateFoodPost mateFoodPost);

    // 특정 MateFoodPost에 속한 모든 댓글 삭제 (게시글 삭제 시 사용)
    void deleteByMateFoodPost(MateFoodPost mateFoodPost);
}