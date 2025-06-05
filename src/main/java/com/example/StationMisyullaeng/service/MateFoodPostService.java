package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.MatePostDto;
import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.repository.MateFoodPostRepository;
import com.example.StationMisyullaeng.repository.KakaoUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MateFoodPostService {

    private final MateFoodPostRepository mateFoodPostRepository;
    private final KakaoUserRepository kakaoUserRepository;

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public List<MatePostDto> getAllPosts() {
        return mateFoodPostRepository.findAll().stream()
                .map(MatePostDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 ID 게시글 조회
    @Transactional(readOnly = true)
    public MatePostDto getPostById(Long postId) {
        MateFoodPost post = mateFoodPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        return MatePostDto.fromEntity(post);
    }

    // 새 게시글 생성
    @Transactional
    public MatePostDto createPost(MatePostDto matePostDto) {
        KakaoUser kakaoUser = kakaoUserRepository.findById(matePostDto.getKakaoUserId())
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다: " + matePostDto.getKakaoUserId()));

        MateFoodPost newPost = MateFoodPost.builder()
                // ★★★ writer 필드를 KakaoUser의 닉네임으로 설정 (DB 스키마에 writer 컬럼 존재) ★★★
                .writer(kakaoUser.getNickname())
                .title(matePostDto.getTitle())
                .content(matePostDto.getContent())
                .meetingStation(matePostDto.getMeetingStation())
                .meetingTime(matePostDto.getMeetingTime())
                .recruitCount(matePostDto.getRecruitCount())
                .preferredGender(matePostDto.getPreferredGender())
                .status("모집 중")
                .kakaoUser(kakaoUser) // KakaoUser 엔티티 직접 매핑
                .build();
        MateFoodPost savedPost = mateFoodPostRepository.save(newPost);
        return MatePostDto.fromEntity(savedPost);
    }

    // 게시글 수정 (이전 제안과 동일)
    @Transactional
    public MatePostDto updatePost(Long postId, MatePostDto matePostDto, Long currentKakaoUserId) {
        MateFoodPost existingPost = mateFoodPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        if (!existingPost.getKakaoUser().getId().equals(currentKakaoUserId)) {
            throw new IllegalArgumentException("게시글을 수정할 권한이 없습니다.");
        }

        // writer 필드도 DTO에서 받은 값으로 업데이트 (선택 사항)
        existingPost.setWriter(matePostDto.getWriter()); // DTO의 writer 값을 엔티티에 반영

        existingPost.setTitle(matePostDto.getTitle());
        existingPost.setContent(matePostDto.getContent());
        existingPost.setMeetingStation(matePostDto.getMeetingStation());
        existingPost.setMeetingTime(matePostDto.getMeetingTime());
        existingPost.setRecruitCount(matePostDto.getRecruitCount());
        existingPost.setPreferredGender(matePostDto.getPreferredGender());
        existingPost.setStatus(matePostDto.getStatus());

        MateFoodPost savedPost = mateFoodPostRepository.save(existingPost);
        return MatePostDto.fromEntity(savedPost);
    }

    // 게시글 삭제 (이전 제안과 동일)
    @Transactional
    public void deletePost(Long postId, Long requestingKakaoUserId) {
        MateFoodPost postToDelete = mateFoodPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 게시글을 찾을 수 없습니다: " + postId));

        if (!postToDelete.getKakaoUser().getId().equals(requestingKakaoUserId)) {
            throw new IllegalArgumentException("게시글을 삭제할 권한이 없습니다.");
        }

        mateFoodPostRepository.deleteById(postId);
    }

    // 새로운 서비스 메서드: 게시글 상태 변경 (이전 제안과 동일)
    @Transactional
    public MatePostDto updatePostStatus(Long postId, String newStatus, Long currentKakaoUserId) {
        MateFoodPost post = mateFoodPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        if (!post.getKakaoUser().getId().equals(currentKakaoUserId)) {
            throw new IllegalArgumentException("게시글 상태를 변경할 권한이 없습니다.");
        }

        if (!"모집 완료".equals(newStatus) && !"모집 중".equals(newStatus)) {
            throw new IllegalArgumentException("유효하지 않은 게시글 상태입니다: " + newStatus);
        }

        post.setStatus(newStatus);
        MateFoodPost updatedPost = mateFoodPostRepository.save(post);
        return MatePostDto.fromEntity(updatedPost);
    }

    public List<MateFoodPost> findPostsByUserId(Long userId) {
        return mateFoodPostRepository.findByUserId(userId);
        // 만약 연관관계면 findByUser_Id(userId)
    }
}