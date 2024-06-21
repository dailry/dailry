package com.daily.daily.post.repository;

import com.daily.daily.post.domain.HotHashtag;
import com.daily.daily.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostRepositoryQuerydsl {

    Slice<Post> findSlice(Pageable pageable);
    void deletePostAndRelatedEntities(Long postId);
    Slice<Post> findPostsByHashtag(List<String> hashtag, Pageable pageable);

    Slice<Post> findPostsByHashtag(Long memberId, Pageable pageable);
}
