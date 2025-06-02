// src/main/java/com/example/StationMisyullaeng/repository/MateFoodPostRepository.java
package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.MateFoodPost;
import org.springframework.data.jpa.repository.JpaRepository; // ❗️ JpaRepository 임포트
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateFoodPostRepository extends JpaRepository<MateFoodPost, Long> { // ❗️ JpaRepository<엔티티타입, ID타입> 상속
    // 필요에 따라 커스텀 쿼리 메서드 추가 가능
    // 예: 최신순으로 정렬하여 모든 게시글 조회
    List<MateFoodPost> findAllByOrderByCreatedAtDesc();
}