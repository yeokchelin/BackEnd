package com.example.StationMisyullaeng.controller;

import com.example.StationMisyullaeng.entity.MateFoodPostComment;
import com.example.StationMisyullaeng.service.MateFoodPostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mateFoodPost/{postId}/comments")
@RequiredArgsConstructor
public class MateFoodPostCommentController {

    private final MateFoodPostCommentService commentService;

    @PostMapping
    public MateFoodPostComment createComment(
            @PathVariable Long postId,
            @RequestBody MateFoodPostComment commentBody
    ) {
        return commentService.createComment(postId, commentBody.getWriter(), commentBody.getContent());
    }

    @GetMapping
    public List<MateFoodPostComment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
}