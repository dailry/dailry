package com.daily.daily.post.controller;

import com.daily.daily.post.dto.HotPostReadSliceResponseDTO;
import com.daily.daily.post.service.HotPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotPosts")
@RequiredArgsConstructor
public class HotPostController {

    private final HotPostService hotPostService;
    @GetMapping
    public HotPostReadSliceResponseDTO readSlice(Pageable pageable) {
        return hotPostService.findSlice(pageable);
    }
}
