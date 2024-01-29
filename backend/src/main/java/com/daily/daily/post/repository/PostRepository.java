package com.daily.daily.post.repository;

import com.daily.daily.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p " +
            "left join fetch p.postWriter pw " +
            "left join fetch p.postHashtags ph " +
            "left join fetch ph.hashtag h"
    )
    Slice<Post> find(Pageable pageable);
}
