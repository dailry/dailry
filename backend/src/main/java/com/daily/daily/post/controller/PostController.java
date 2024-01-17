package com.daily.daily.post.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.post.dto.PostCreateDTO;
import com.daily.daily.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @Secured(value = "ROLE_MEMBER")
    public SuccessResponseDTO createPost(
            @AuthenticationPrincipal Long id,
            @RequestPart @Valid PostCreateDTO postCreateDTO,
            @RequestPart MultipartFile pageImage
    ) {
        System.out.println(postCreateDTO.getContent());
        System.out.println(pageImage);

        postService.create(id, postCreateDTO, pageImage);

        return new SuccessResponseDTO(201);
    }
}
