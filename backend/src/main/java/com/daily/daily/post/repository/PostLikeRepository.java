package com.daily.daily.post.repository;

import com.daily.daily.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select pl from PostLike pl where pl.post.id = :postId and pl.member.id = :memberId")
    Optional<PostLike> findBy(Long memberId, Long postId);

    @Query("select pl from PostLike pl where pl.member.id = :memberId and pl.post.id in :postIds")
    List<PostLike> findByMemberIdAndPostIds(Long memberId, List<Long> postIds);
}

