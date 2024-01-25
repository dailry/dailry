package com.daily.daily.post.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.post.dto.PostReadResponseDTO;
import com.daily.daily.post.dto.PostRequestDTO;
import com.daily.daily.post.dto.PostWriteResponseDTO;
import com.daily.daily.post.service.PostService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor

//TODO: 게시글 수정과 삭제는, 요청을 보낸사람의 신원을 확인해야 한다.
// 자신이 작성한 게시글에 한해서, 수정과 삭제를 요청할 수 있다.
public class PostController {

    private final PostService postService;

    @PostMapping
    @Secured(value = "ROLE_MEMBER")
    @ResponseStatus(HttpStatus.CREATED)
    public PostWriteResponseDTO createPost(
            @AuthenticationPrincipal Long id,
            @RequestPart @Valid PostRequestDTO request,
            @RequestPart @NotNull MultipartFile pageImage
    ) {
        return postService.create(id, request, pageImage);
    }

    @PostMapping("/{postId}/edit")
    @Secured(value = "ROLE_MEMBER")
    public PostWriteResponseDTO updatePost(
            @PathVariable Long postId,
            @RequestPart @Valid PostRequestDTO request,
            @RequestPart @Nullable MultipartFile pageImage
    ) {
        return postService.update(postId, request, pageImage);
    }

    @GetMapping("/{postId}")
    public PostReadResponseDTO readSinglePost(@PathVariable Long postId) {
        return postService.find(postId);
    }

    @DeleteMapping("/{postId}")
    @Secured(value = "ROLE_MEMBER")
    public SuccessResponseDTO deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return new SuccessResponseDTO();
    }
}
