package com.example.StationMisyullaeng.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MateFoodComment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateFoodPostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference
    private MateFoodPost mateFoodPost;

    @Column(nullable = false, length = 50)
    private String writer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}