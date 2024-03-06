package com.daily.daily.post.repository;

import com.daily.daily.post.domain.HotPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface HotPostRepositoryQuerydsl {
    Slice<HotPost> findSlice(Pageable pageable);

}
