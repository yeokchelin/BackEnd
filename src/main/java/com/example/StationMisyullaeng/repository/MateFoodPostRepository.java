// src/main/java/com/example/StationMisyullaeng/repository/MatePostRepository.java
package com.example.StationMisyullaeng.repository; // 실제 프로젝트 패키지 경로로 수정하세요.

import com.example.StationMisyullaeng.entity.MateFoodPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateFoodPostRepository extends JpaRepository<MateFoodPost, Long> {
    // 필요에 따라 커스텀 쿼리 메서드 추가 가능
    // 예: 최신순으로 정렬하여 모든 게시글 조회
    List<MateFoodPost> findAllByOrderByCreatedAtDesc();

    List<MateFoodPost> findByUserId(Long userId); // userId가 작성자 PK라고 가정!
}