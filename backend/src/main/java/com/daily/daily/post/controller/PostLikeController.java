package com.daily.daily.post.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.post.service.PostLikeFacade;
import com.daily.daily.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    private final PostLikeFacade postLikeFacade;
    @PostMapping("/{postId}/likes")
    public SuccessResponseDTO increaseLikeCount(@AuthenticationPrincipal Long memberId, @PathVariable Long postId) throws InterruptedException {
        postLikeFacade.increaseLikeCount(memberId, postId);
        return new SuccessResponseDTO();
    }

    @DeleteMapping("/{postId}/likes")
    public SuccessResponseDTO decreaseLikeCount(@AuthenticationPrincipal Long memberId, @PathVariable Long postId) throws InterruptedException {
        postLikeFacade.decreaseLikeCount(memberId, postId);
        return new SuccessResponseDTO();
    }

    @GetMapping("/likes")
    public Map<Long, Boolean> getLikeStatus(@AuthenticationPrincipal Long memberId, @RequestParam("postIds") List<Long> postIds) {
        return postLikeService.getLikeStatus(memberId, postIds);
    }
}
