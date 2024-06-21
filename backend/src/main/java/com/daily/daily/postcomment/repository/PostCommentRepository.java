package com.daily.daily.postcomment.repository;

import com.daily.daily.postcomment.domain.PostComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @Query("select c from PostComment c where c.post.id = :postId")
    Slice<PostComment> findByPostId(Long postId, Pageable pageable);

    @Query("select c from PostComment c where c.commentWriter.id = :memberId")
    Slice<PostComment> findCommentsByMemberId(Long memberId, Pageable pageable);
}
