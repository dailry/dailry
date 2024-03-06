package com.daily.daily.post.service;

import com.daily.daily.post.domain.HotPost;
import com.daily.daily.post.dto.HotPostReadSliceResponseDTO;
import com.daily.daily.post.repository.HotPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HotPostService {

    private final HotPostRepository hotPostRepository;

    public HotPostReadSliceResponseDTO findSlice(Pageable pageable) {
        Slice<HotPost> hotPosts = hotPostRepository.findSlice(pageable);
        return null;
    }
}
