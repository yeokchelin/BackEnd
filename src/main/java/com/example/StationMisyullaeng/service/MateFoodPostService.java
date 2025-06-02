// src/main/java/com/example/StationMisyullaeng/service/MateFoodPostService.java
package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.MatePostDto;
import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.repository.MateFoodPostRepository;
import com.example.StationMisyullaeng.repository.MateFoodPostCommentRepository; // 댓글 삭제를 위해 필요
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 클래스 레벨에는 기본적으로 읽기 전용 트랜잭션 설정
public class MateFoodPostService {

    private final MateFoodPostRepository mateFoodPostRepository; // MateFoodPost 리포지토리 인스턴스
    private final MateFoodPostCommentRepository mateFoodPostCommentRepository; // MateFoodPostComment 리포지토리 인스턴스

    // 모든 게시글 조회 (최신순)
    public List<MatePostDto> getAllPosts() {
        return mateFoodPostRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(MatePostDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 ID의 게시글 조회
    public MatePostDto getPostById(Long postId) {
        MateFoodPost entity = mateFoodPostRepository.findById(postId) // ❗️ 인스턴스 변수 사용
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글을 찾을 수 없습니다: " + postId));
        return MatePostDto.fromEntity(entity);
    }

    // 새 게시글 생성
    @Transactional // 데이터 변경(쓰기)이 있으므로 트랜잭션 적용
    public MatePostDto createPost(MatePostDto matePostDto) {
        // 기본적인 유효성 검사
        if (!StringUtils.hasText(matePostDto.getWriter())) {
            throw new IllegalArgumentException("작성자(writer)는 비워둘 수 없습니다.");
        }
        if (!StringUtils.hasText(matePostDto.getTitle())) {
            throw new IllegalArgumentException("제목(title)은 비워둘 수 없습니다.");
        }
        if (!StringUtils.hasText(matePostDto.getContent())) {
            throw new IllegalArgumentException("내용(content)은 비워둘 수 없습니다.");
        }
        if (!StringUtils.hasText(matePostDto.getMeetingStation())) {
            throw new IllegalArgumentException("만날 역(meetingStation)은 비워둘 수 없습니다.");
        }
        if (!StringUtils.hasText(matePostDto.getMeetingTime())) {
            throw new IllegalArgumentException("만날 시간(meetingTime)은 비워둘 수 없습니다.");
        }
        if (matePostDto.getRecruitCount() == null || matePostDto.getRecruitCount() <= 0) {
            throw new IllegalArgumentException("모집 인원(recruitCount)은 0보다 커야 합니다.");
        }
        if (!StringUtils.hasText(matePostDto.getPreferredGender())) {
            throw new IllegalArgumentException("선호 성별(preferredGender)은 비워둘 수 없습니다.");
        }

        String status = StringUtils.hasText(matePostDto.getStatus()) ? matePostDto.getStatus() : "모집 중";

        MateFoodPost entity = MateFoodPost.builder()
                .writer(matePostDto.getWriter())
                .title(matePostDto.getTitle())
                .content(matePostDto.getContent())
                .meetingStation(matePostDto.getMeetingStation())
                .meetingTime(matePostDto.getMeetingTime())
                .recruitCount(matePostDto.getRecruitCount())
                .preferredGender(matePostDto.getPreferredGender())
                .status(status)
                .build();

        MateFoodPost savedEntity = mateFoodPostRepository.save(entity); // ❗️ 인스턴스 변수 사용
        return MatePostDto.fromEntity(savedEntity);
    }

    // 게시글 수정
    @Transactional // 데이터 변경(쓰기)이 있으므로 트랜잭션 적용
    public MatePostDto updatePost(Long postId, MatePostDto matePostDto) {
        MateFoodPost existingEntity = mateFoodPostRepository.findById(postId) // ❗️ 인스턴스 변수 사용
                .orElseThrow(() -> new IllegalArgumentException("수정할 게시글을 찾을 수 없습니다: " + postId));

        // TODO: 권한 검증 (예: 현재 사용자가 게시글 작성자인지)

        if (StringUtils.hasText(matePostDto.getTitle())) existingEntity.setTitle(matePostDto.getTitle());
        if (StringUtils.hasText(matePostDto.getContent())) {
            existingEntity.setContent(matePostDto.getContent());
        } else if (matePostDto.getContent() != null && matePostDto.getContent().isEmpty()){
            throw new IllegalArgumentException("게시글 내용(content)은 비워둘 수 없습니다.");
        }
        if (StringUtils.hasText(matePostDto.getMeetingStation())) existingEntity.setMeetingStation(matePostDto.getMeetingStation());
        if (StringUtils.hasText(matePostDto.getMeetingTime())) existingEntity.setMeetingTime(matePostDto.getMeetingTime());
        if (matePostDto.getRecruitCount() != null && matePostDto.getRecruitCount() > 0) existingEntity.setRecruitCount(matePostDto.getRecruitCount());
        if (StringUtils.hasText(matePostDto.getPreferredGender())) existingEntity.setPreferredGender(matePostDto.getPreferredGender());
        if (StringUtils.hasText(matePostDto.getStatus())) existingEntity.setStatus(matePostDto.getStatus());

        return MatePostDto.fromEntity(existingEntity);
    }

    // 게시글 삭제 (댓글을 먼저 삭제하도록 수정됨)
    @Transactional // 데이터 변경(쓰기)이 있으므로 트랜잭션 적용
    public void deletePost(Long postId) {
        MateFoodPost postToDelete = mateFoodPostRepository.findById(postId) // ❗️ 인스턴스 변수 사용
                .orElseThrow(() -> new IllegalArgumentException("삭제할 게시글을 찾을 수 없습니다: " + postId));

        // TODO: 권한 검증

        // 1. 해당 게시글에 달린 모든 댓글을 먼저 삭제합니다.
        mateFoodPostCommentRepository.deleteByMateFoodPost(postToDelete); // ❗️ 인스턴스 변수 사용

        // 2. 댓글이 모두 삭제된 후 게시글을 삭제합니다.
        mateFoodPostRepository.delete(postToDelete); // ❗️ 인스턴스 변수 사용
    }

    // 게시글 상태 변경 (예: "모집 완료")
    @Transactional // 데이터 변경(쓰기)이 있으므로 트랜잭션 적용
    public MatePostDto updatePostStatus(Long postId, String newStatus) {
        MateFoodPost existingEntity = mateFoodPostRepository.findById(postId) // ❗️ 인스턴스 변수 사용
                .orElseThrow(() -> new IllegalArgumentException("상태를 업데이트할 게시글을 찾을 수 없습니다: " + postId));

        // TODO: 권한 검증

        if (!StringUtils.hasText(newStatus) || (!"모집 중".equals(newStatus) && !"모집 완료".equals(newStatus))) {
            throw new IllegalArgumentException("유효하지 않은 상태 값입니다: " + newStatus);
        }
        existingEntity.setStatus(newStatus);
        return MatePostDto.fromEntity(existingEntity);
    }
}