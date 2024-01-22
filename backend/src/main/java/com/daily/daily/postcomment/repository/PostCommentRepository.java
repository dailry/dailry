package com.daily.daily.postcomment.repository;

import com.daily.daily.postcomment.domain.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @Query("select c from PostComment c where c.post.id = :postId")
    Page<PostComment> findByPostId(Long postId, Pageable pageable);
}
