package com.daily.daily.postcomment.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.postcomment.dto.PostCommentRequestDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import com.daily.daily.postcomment.service.PostCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            @RequestBody @Valid PostCommentRequestDTO postCommentRequestDTO
    ) {
        return commentService.create(writerId, postId, postCommentRequestDTO);
    }

    @PatchMapping("/posts/comments/{commentId}")
    public PostCommentResponseDTO update(
            @AuthenticationPrincipal Long writerId,
            @PathVariable Long commentId,
            @RequestBody @Valid PostCommentRequestDTO postCommentRequestDTO
    ) {
        return commentService.update(writerId, commentId, postCommentRequestDTO);
    }

    @DeleteMapping("/posts/comments/{commentId}")
    public SuccessResponseDTO delete(
            @AuthenticationPrincipal Long deleterId,
            @PathVariable Long commentId
    ) {
        commentService.delete(deleterId, commentId);
        return new SuccessResponseDTO();
    }
}
