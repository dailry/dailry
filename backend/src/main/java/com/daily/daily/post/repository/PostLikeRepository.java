package com.daily.daily.post.repository;

import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    //TODO: 현재는 select count로 존재 여부를 처리하지만 성능상 좋지 않다.
    //      해당 조건에 맞는 row가 존재해도 그 자리에서 return 을 하는 것이 아니라 계속 순회하며 count를 세기 때문.
    //      추후에 Querydsl 이용해서 최적화가 필요함
    
    @Query("select count(pl.id) > 0 from PostLike pl where pl.post.id = :postId and pl.member.id = :memberId")
    boolean existsBy(Long memberId, Long postId);

    @Query("select pl from PostLike pl where pl.post.id = :postId and pl.member.id = :memberId")
    Optional<PostLike> findBy(Long memberId, Long postId);

    Long countByPost(Post post);
}

