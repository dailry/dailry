package com.daily.daily.postcomment.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.postcomment.dto.PostCommentSliceDTO;
import com.daily.daily.postcomment.dto.PostCommentRequestDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import com.daily.daily.postcomment.service.PostCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostCommentController {

    private final PostCommentService commentService;

    @Secured(value = "ROLE_MEMBER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{postId}/comments")
    public PostCommentResponseDTO create(
            @AuthenticationPrincipal Long writerId,
            @PathVariable Long postId,
            @RequestBody @Valid PostCommentRequestDTO postCommentRequestDTO
    ) {
        return commentService.create(writerId, postId, postCommentRequestDTO);
    }

    @Secured(value = "ROLE_MEMBER")
    @PatchMapping("/comments/{commentId}")
    public PostCommentResponseDTO update(
            @AuthenticationPrincipal Long writerId,
            @PathVariable Long commentId,
            @RequestBody @Valid PostCommentRequestDTO postCommentRequestDTO
    ) {
        return commentService.update(writerId, commentId, postCommentRequestDTO);
    }

    @GetMapping("/{postId}/comments")
    public PostCommentSliceDTO readByPostId(
            @PathVariable Long postId,
            Pageable pageable
    ) {
        return commentService.readByPostId(postId, pageable);
    }

    @Secured(value = "ROLE_MEMBER")
    @DeleteMapping("/comments/{commentId}")
    public SuccessResponseDTO delete(
            @AuthenticationPrincipal Long deleterId,
            @PathVariable Long commentId
    ) {
        commentService.delete(deleterId, commentId);
        return new SuccessResponseDTO();
    }

    @GetMapping("member/{memberId}/comments")
    public PostCommentSliceDTO readCommentsByMember(
            @PathVariable Long memberId,
            Pageable pageable
    ) {
        return commentService.readCommentsByMemberId(memberId, pageable);
    }
}
