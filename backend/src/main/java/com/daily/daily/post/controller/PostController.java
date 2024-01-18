package com.daily.daily.post.controller;

import com.daily.daily.post.dto.PostRequestDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import com.daily.daily.post.service.PostService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @Secured(value = "ROLE_MEMBER")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDTO createPost(
            @AuthenticationPrincipal Long id,
            @RequestPart @Valid PostRequestDTO request,
            @RequestPart @NotNull MultipartFile pageImage
    ) {
        return postService.create(id, request, pageImage);
    }

    @PostMapping("/{postId}/edit")
    @Secured(value = "ROLE_MEMBER")
    public PostResponseDTO updatePost(
            @PathVariable Long postId,
            @RequestPart @Valid PostRequestDTO request,
            @RequestPart @Nullable MultipartFile pageImage
    ) {
        return postService.update(postId, request, pageImage);
    }
}
