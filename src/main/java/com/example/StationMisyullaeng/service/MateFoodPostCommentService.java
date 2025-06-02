// src/main/java/com/example/StationMisyullaeng/service/MateFoodPostCommentService.java
package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.CommentRequestDto;  // 요청 DTO 사용
import com.example.StationMisyullaeng.dto.CommentResponseDto; // 응답 DTO 사용
import com.example.StationMisyullaeng.entity.MateFoodPost;
import com.example.StationMisyullaeng.entity.MateFoodPostComment;
import com.example.StationMisyullaeng.repository.MateFoodPostCommentRepository;
import com.example.StationMisyullaeng.repository.MateFoodPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils; // StringUtils.hasText 사용

// import org.springframework.security.access.AccessDeniedException; // 권한 예외
// import org.springframework.security.core.context.SecurityContextHolder; // 현재 사용자 정보

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional // 클래스 레벨 트랜잭션 (기본 readOnly=false)
public class MateFoodPostCommentService {

    private final MateFoodPostRepository postRepository;
    private final MateFoodPostCommentRepository commentRepository;

    // ... (createComment, getCommentsByPostId 메서드는 이전과 동일)

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto) {
        MateFoodPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 작성할 게시글을 찾을 수 없습니다. ID: " + postId));

        // String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        // if(!currentUsername.equals(requestDto.getWriter())) { /* 권한 문제 처리 */ }

        MateFoodPostComment comment = MateFoodPostComment.builder()
                .writer(requestDto.getWriter()) // TODO: 실제로는 인증된 사용자 정보로 설정
                .content(requestDto.getContent())
                .mateFoodPost(post)
                .build();
        MateFoodPostComment savedComment = commentRepository.save(comment);
        return CommentResponseDto.fromEntity(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        MateFoodPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 조회할 게시글을 찾을 수 없습니다. ID: " + postId));
        return commentRepository.findByMateFoodPostOrderByCreatedAtDesc(post).stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // ❗️ 추가: 댓글 수정 메서드
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto) {
        // 게시글 존재 여부 확인 (선택적이지만, 댓글이 올바른 게시글에 속하는지 확인 가능)
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("댓글이 속한 게시글을 찾을 수 없습니다. ID: " + postId);
        }

        MateFoodPostComment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("수정할 댓글을 찾을 수 없습니다. ID: " + commentId));

        // TODO: 실제 애플리케이션에서는 현재 인증된 사용자가 댓글 작성자인지, 또는 관리자인지 권한 검증 필요
        // String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        // if (!existingComment.getWriter().equals(currentUsername) /* && !currentUser.hasRole("ADMIN") */ ) {
        //     throw new AccessDeniedException("댓글을 수정할 권한이 없습니다.");
        // }

        // 댓글이 해당 게시글의 댓글인지 추가 확인 (선택적)
        if (!existingComment.getMateFoodPost().getId().equals(postId)) {
            throw new IllegalArgumentException("요청된 게시글 ID와 댓글이 속한 게시글 ID가 일치하지 않습니다.");
        }

        // 내용(content) 필드만 업데이트한다고 가정
        if (StringUtils.hasText(requestDto.getContent())) {
            existingComment.setContent(requestDto.getContent());
        } else {
            throw new IllegalArgumentException("댓글 내용(content)은 비워둘 수 없습니다.");
        }
        // writer는 일반적으로 수정하지 않음
        // updatedAt은 엔티티의 @UpdateTimestamp로 자동 업데이트

        // save를 명시적으로 호출하지 않아도, 트랜잭션 종료 시 변경 감지(dirty checking)로 업데이트됨
        return CommentResponseDto.fromEntity(existingComment);
    }

    // 댓글 삭제 메서드 (이전과 동일)
    public void deleteComment(Long postId, Long commentId) { /* 이전 답변의 코드와 동일 */ }
}