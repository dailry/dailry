package com.daily.daily.post.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.post.dto.PostCreateDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import com.daily.daily.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Secured(value = "ROLE_MEMBER")
    public PostResponseDTO createPost(
            @AuthenticationPrincipal Long id,
            @RequestPart @Valid PostCreateDTO postCreateRequest,
            @RequestPart MultipartFile pageImage
    ) {
        return postService.create(id, postCreateRequest, pageImage);
    }
}
