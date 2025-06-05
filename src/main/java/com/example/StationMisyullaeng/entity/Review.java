package com.example.StationMisyullaeng.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    // KakaoUser와의 관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private KakaoUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Column(name = "image_path", length = 255)
    private String imagePath;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ★★★ 점주 답글 필드 추가 ★★★
    @Column(name = "owner_reply_content", columnDefinition = "TEXT")
    private String ownerReplyContent;

    @Column(name = "owner_replied_at")
    private LocalDateTime ownerRepliedAt;

    // 편의 메서드: 답글 업데이트
    public void updateOwnerReply(String replyContent) {
        this.ownerReplyContent = replyContent;
        this.ownerRepliedAt = LocalDateTime.now();
    }

    // 답글 삭제 (필요시)
    public void clearOwnerReply() {
        this.ownerReplyContent = null;
        this.ownerRepliedAt = null;
    }
}