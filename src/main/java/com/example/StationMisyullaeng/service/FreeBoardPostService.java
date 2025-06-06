package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.FreePostDto;
import com.example.StationMisyullaeng.entity.FreePostWrite;
import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.repository.FreePostRepository; // 이 부분이 FreePostWriteRepository일 수도 있습니다.
import com.example.StationMisyullaeng.repository.KakaoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreeBoardPostService {

    private final FreePostRepository freePostWriteRepository; // 실제 레포지토리 이름에 맞게 조정하세요.
    private final KakaoUserRepository kakaoUserRepository;

    // 게시글 생성
    @Transactional
    public FreePostDto createFreePost(FreePostDto freePostDto) {
        KakaoUser kakaoUser = kakaoUserRepository.findById(freePostDto.getKakaoUserId())
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다: " + freePostDto.getKakaoUserId()));


        FreePostWrite freePostWrite = FreePostWrite.builder()
                .title(freePostDto.getTitle())
                .content(freePostDto.getContent())
                .writer(freePostDto.getWriter())
                // .kakaoUserId(freePostDto.getKakaoUserId()) // FreePostWrite 엔티티에 kakaoUserId 필드가 있는지 확인 필요
                .createdAt(LocalDateTime.now())
                .user(kakaoUser)
                .build();
        FreePostWrite savedPost = freePostWriteRepository.save(freePostWrite);
        return FreePostDto.fromEntity(savedPost);
    }

    // 모든 게시글 조회
    @Transactional(readOnly = true)
    public List<FreePostDto> getAllFreePosts() {
        return freePostWriteRepository.findAll().stream()
                .map(FreePostDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 게시글 조회
    @Transactional(readOnly = true)
    public FreePostDto getPostById(Long postId) {
        FreePostWrite freePostWrite = freePostWriteRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        return FreePostDto.fromEntity(freePostWrite);
    }

    // 게시글 수정
    @Transactional
    public FreePostDto updatePost(Long postId, FreePostDto updatedPostDto) {
        FreePostWrite existingPost = freePostWriteRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        existingPost.setTitle(updatedPostDto.getTitle());
        existingPost.setContent(updatedPostDto.getContent());
        // existingPost.update(updatedPostDto.getTitle(), updatedPostDto.getContent());

        FreePostWrite savedPost = freePostWriteRepository.save(existingPost);
        return FreePostDto.fromEntity(savedPost);
    }

    // ★★★ 이 메서드에 디버그 로그를 추가했습니다. ★★★
    @Transactional
    public void deletePost(Long postId) {
        System.out.println("DEBUG: deletePost 메서드 호출됨. postId: " + postId); // ❗️ 추가 로그 1

        // ID로 게시글을 찾아서 존재 여부 확인
        FreePostWrite postToDelete = freePostWriteRepository.findById(postId)
                .orElseThrow(() -> {
                    System.err.println("ERROR: 삭제할 게시글을 찾을 수 없습니다. ID: " + postId); // ❗️ 추가 로그 2
                    // Exception을 다시 던질 때, 어떤 예외가 발생하는지 프론트엔드에 전달될 수 있도록 message를 포함합니다.
                    return new IllegalArgumentException("삭제할 게시글을 찾을 수 없습니다: " + postId);
                });

        System.out.println("DEBUG: 게시글 찾기 성공. 제목: " + postToDelete.getTitle() + ", ID: " + postToDelete.getId()); // ❗️ 추가 로그 3

        try {
            freePostWriteRepository.delete(postToDelete);
            System.out.println("DEBUG: 게시글 삭제 요청 완료. ID: " + postId); // ❗️ 추가 로그 4
        } catch (Exception e) {
            System.err.println("ERROR: 게시글 삭제 중 예외 발생. ID: " + postId + ", 예외: " + e.getMessage()); // ❗️ 추가 로그 5 (삭제 자체의 문제)
            throw new RuntimeException("게시글 삭제 중 오류가 발생했습니다.", e); // 런타임 예외로 다시 던짐
        }
    }

    public List<FreePostDto> findPostsByUserId(Long userId) {
        return freePostWriteRepository.findByUserId(userId)
                .stream()
                .map(FreePostDto::fromEntity)
                .collect(Collectors.toList());
    }
}