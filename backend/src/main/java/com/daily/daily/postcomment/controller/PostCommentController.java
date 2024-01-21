package com.daily.daily.postcomment.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.postcomment.dto.PostCommentRequestDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import com.daily.daily.postcomment.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostCommentController {

    private final PostCommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/{postId}/comments")
    public PostCommentResponseDTO create(
            @AuthenticationPrincipal Long writerId,
            @PathVariable Long postId,
            PostCommentRequestDTO postCommentRequestDTO
    ) {
        return commentService.create(writerId, postId, postCommentRequestDTO);
    }

    @PatchMapping("/comments/{commentId}")
    public PostCommentResponseDTO update(
            @AuthenticationPrincipal Long writerId,
            @PathVariable Long commentId,
            PostCommentRequestDTO postCommentRequestDTO
    ) {
        return commentService.update(writerId, commentId, postCommentRequestDTO);
    }

    @DeleteMapping("/comments/{commentId}")
    public SuccessResponseDTO delete(
            @AuthenticationPrincipal Long deleterId,
            @PathVariable Long commentId
    ) {
        commentService.delete(deleterId, commentId);
        return new SuccessResponseDTO();
    }
}
