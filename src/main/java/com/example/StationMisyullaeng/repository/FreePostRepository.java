package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.FreePostWrite; // ❗️ PostWrite 대신 FreePostWrite 임포트
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // ❗️ List 임포트 추가

@Repository
public interface FreePostRepository extends JpaRepository<FreePostWrite, Long> { // ❗️ 클래스 이름 변경
    // ❗️ 모든 게시글을 생성일(createdAt) 기준으로 내림차순(최신순) 정렬하여 조회하는 메서드 추가
    List<FreePostWrite> findAllByOrderByCreatedAtDesc();

    List<FreePostWrite> findByUserId(Long userId);
}