package com.daily.daily.post.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.post.dto.*;
import com.daily.daily.post.service.HashtagService;
import com.daily.daily.post.service.PostService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final HashtagService hashtagService;

    @PostMapping
    @Secured(value = "ROLE_MEMBER")
    @ResponseStatus(HttpStatus.CREATED)
    public PostWriteResponseDTO createPost(
            @AuthenticationPrincipal Long id,
            @RequestPart @Valid PostWriteRequestDTO request,
            @RequestPart @NotNull MultipartFile pageImage
    ) {
        return postService.create(id, request, pageImage);
    }

    @PostMapping("/{postId}/edit")
    @Secured(value = "ROLE_MEMBER")
    public PostWriteResponseDTO updatePost(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long postId,
            @RequestPart @Valid PostWriteRequestDTO request,
            @RequestPart @Nullable MultipartFile pageImage
    ) {
        return postService.update(memberId, postId, request, pageImage);
    }

    @GetMapping("/{postId}")
    public PostReadResponseDTO readSinglePost(@PathVariable Long postId) {
        return postService.find(postId);
    }

    @GetMapping
    public PostReadSliceResponseDTO readSlicePost(Pageable pageable) {
        return postService.findSlice(pageable);
    }


    @DeleteMapping("/{postId}")
    @Secured(value = "ROLE_MEMBER")
    public SuccessResponseDTO deletePost(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long postId
    ) {
        postService.delete(memberId, postId);
        return new SuccessResponseDTO();
    }

    @GetMapping("/search")
    public PostReadSliceResponseDTO readPostByHashTag(@RequestParam("hashtags") List<String> hashtags, Pageable pageable) {
        return postService.findPostByHashtag(hashtags, pageable);
    }

    @GetMapping("/hotHashtags")
    public HotHashtagReadListResponseDTO readHotHashTags() {
        return hashtagService.findHotHashTags();
    }

    @GetMapping("member/{memberId}")
    public PostReadSliceResponseDTO readPostByMember(@PathVariable Long memberId, Pageable pageable) {
        return postService.findPostByMember(memberId, pageable);
    }

}
