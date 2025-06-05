package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.MatePostDto; // 실제 DTO 경로로 수정하세요.
import com.example.StationMisyullaeng.entity.MateFoodPost; // 실제 엔티티 경로
import com.example.StationMisyullaeng.repository.MateFoodPostRepository; // 실제 레포지토리 경로
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관리를 위해 추가

import java.time.LocalDateTime; // 필요한 경우 LocalDateTime import
import java.util.List;

@Service
@RequiredArgsConstructor
public class MateFoodPostService {

    private final MateFoodPostRepository mateFoodPostRepository;

    // 예시: DTO 변환을 위한 정적 팩토리 메서드가 MatePostDto에 있다고 가정
    // public static MatePostDto fromEntity(MateFoodPost entity) { ... }

    // 기존의 모든 서비스 메서드는 그대로 유지
    // 예시: 전체 게시글 조회
    public List<MatePostDto> getAllPosts() {
        return mateFoodPostRepository.findAll().stream()
                .map(MatePostDto::fromEntity) // 엔티티를 DTO로 변환
                .toList();
    }

    // 예시: 특정 ID 게시글 조회
    public MatePostDto getPostById(Long postId) {
        MateFoodPost post = mateFoodPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        return MatePostDto.fromEntity(post);
    }

    // 예시: 새 게시글 생성
    @Transactional
    public MatePostDto createPost(MatePostDto matePostDto) {
        // DTO를 엔티티로 변환하여 저장
        MateFoodPost newPost = MateFoodPost.builder()
                .writer(matePostDto.getWriter())
                .title(matePostDto.getTitle())
                .content(matePostDto.getContent())
                .meetingStation(matePostDto.getMeetingStation())
                .meetingTime(matePostDto.getMeetingTime())
                .recruitCount(matePostDto.getRecruitCount())
                .preferredGender(matePostDto.getPreferredGender())
                .status("모집 중") // 초기 상태는 "모집 중"
                .createdAt(LocalDateTime.now()) // 생성 시간 자동 설정
                .build();
        MateFoodPost savedPost = mateFoodPostRepository.save(newPost);
        return MatePostDto.fromEntity(savedPost);
    }

    // 예시: 게시글 수정
    @Transactional
    public MatePostDto updatePost(Long postId, MatePostDto matePostDto) {
        MateFoodPost existingPost = mateFoodPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        existingPost.setTitle(matePostDto.getTitle());
        existingPost.setContent(matePostDto.getContent());
        existingPost.setMeetingStation(matePostDto.getMeetingStation());
        existingPost.setMeetingTime(matePostDto.getMeetingTime());
        existingPost.setRecruitCount(matePostDto.getRecruitCount());
        existingPost.setPreferredGender(matePostDto.getPreferredGender());
        // status는 직접 수정하지 않고, updatePostStatus를 통해 변경하도록 합니다.
        // existingPost.setStatus(matePostDto.getStatus()); // 필요한 경우 주석 해제하여 DTO의 상태도 반영

        MateFoodPost updatedPost = mateFoodPostRepository.save(existingPost);
        return MatePostDto.fromEntity(updatedPost);
    }

    // 예시: 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        if (!mateFoodPostRepository.existsById(postId)) {
            throw new IllegalArgumentException("삭제할 게시글을 찾을 수 없습니다: " + postId);
        }
        mateFoodPostRepository.deleteById(postId);
    }

    // 새로운 서비스 메서드: 게시글 상태 변경
    @Transactional
    public MatePostDto updatePostStatus(Long postId, String newStatus) {
        MateFoodPost post = mateFoodPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        // 상태 변경 로직 추가: 유효한 상태 값인지 확인
        if (!"모집 완료".equals(newStatus) && !"모집 중".equals(newStatus)) {
            throw new IllegalArgumentException("유효하지 않은 게시글 상태입니다: " + newStatus);
        }

        post.setStatus(newStatus); // MateFoodPost 엔티티에 setStatus() 메서드가 정의되어 있어야 합니다.
        MateFoodPost updatedPost = mateFoodPostRepository.save(post);
        return MatePostDto.fromEntity(updatedPost);
    }
}