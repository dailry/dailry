package com.daily.daily.post.repository;

import com.daily.daily.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryQuerydsl {

    Slice<Post> find(Pageable pageable);

}
