package com.example.StationMisyullaeng.service;

import com.example.StationMisyullaeng.dto.CommentDto;
import com.example.StationMisyullaeng.entity.Comment;
import com.example.StationMisyullaeng.entity.KakaoUser; // KakaoUser 임포트
import com.example.StationMisyullaeng.repository.CommentRepository;
import com.example.StationMisyullaeng.repository.KakaoUserRepository; // KakaoUserRepository 임포트

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final KakaoUserRepository kakaoUserRepository; // KakaoUser 조회를 위해 주입

    // 댓글 생성
    @Transactional
    public CommentDto createComment(CommentDto commentDto) {
        // KakaoUser 조회 (commentDto의 kakaoUserId 사용)
        KakaoUser kakaoUser = kakaoUserRepository.findById(commentDto.getKakaoUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + commentDto.getKakaoUserId()));

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .targetId(commentDto.getTargetId())
                .targetType(commentDto.getTargetType())
                .kakaoUser(kakaoUser) // KakaoUser 엔티티 설정
                .createdAt(LocalDateTime.now()) // 현재 시간 자동 설정
                .build();

        Comment savedComment = commentRepository.save(comment);
        // DTO로 변환하여 반환 (작성자 닉네임, 아바타 URL 등 포함)
        return convertToDto(savedComment, kakaoUser.getId()); // isAuthor를 위해 현재 사용자 ID 전달
    }

    // 대상별 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByTarget(Long targetId, String targetType, Long currentKakaoUserId) {
        List<Comment> comments = commentRepository.findByTargetIdAndTargetTypeOrderByCreatedAtAsc(targetId, targetType);

        // 댓글 엔티티를 DTO로 변환하면서 작성자 정보 및 isAuthor 여부 포함
        return comments.stream()
                .map(comment -> convertToDto(comment, currentKakaoUserId))
                .collect(Collectors.toList());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long requestingKakaoUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 댓글 작성자와 요청자가 일치하는지 확인 (권한 체크)
        if (!comment.getKakaoUser().getId().equals(requestingKakaoUserId)) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    // 댓글 수정 (선택 사항)
    @Transactional
    public CommentDto updateComment(Long commentId, String newContent, Long requestingKakaoUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 댓글 작성자와 요청자가 일치하는지 확인 (권한 체크)
        if (!comment.getKakaoUser().getId().equals(requestingKakaoUserId)) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        comment.setContent(newContent);
        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment, requestingKakaoUserId);
    }

    // Comment 엔티티를 CommentDto로 변환하는 헬퍼 메서드
    private CommentDto convertToDto(Comment comment, Long currentKakaoUserId) {
        String writerNickname = comment.getKakaoUser().getNickname();
        // KakaoUser 엔티티에 profileImage 필드가 있다고 가정
        String writerAvatarUrl = comment.getKakaoUser().getProfileImage();

        boolean isAuthor = currentKakaoUserId != null && comment.getKakaoUser().getId().equals(currentKakaoUserId);

        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .targetId(comment.getTargetId())
                .targetType(comment.getTargetType())
                .kakaoUserId(comment.getKakaoUser().getId()) // 작성자 ID도 DTO에 포함
                .writerNickname(writerNickname)
                .writerAvatarUrl(writerAvatarUrl)
                .createdAt(comment.getCreatedAt())
                .isAuthor(isAuthor)
                .build();
    }
}