package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // List 임포트 추가

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // ❗️ 이 메서드가 정확히 여기에 정의되어 있어야 합니다.
    // 특정 targetId와 targetType에 해당하는 댓글들을 생성 시간(createdAt) 오름차순으로 조회
    List<Comment> findByTargetIdAndTargetTypeOrderByCreatedAtAsc(Long targetId, String targetType);
}