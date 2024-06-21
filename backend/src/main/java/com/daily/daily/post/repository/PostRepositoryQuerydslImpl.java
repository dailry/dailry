package com.daily.daily.post.repository;

import com.daily.daily.post.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daily.daily.member.domain.QMember.member;
import static com.daily.daily.post.domain.QPost.post;
import static com.daily.daily.post.domain.QPostHashtag.postHashtag;
import static com.daily.daily.post.domain.QPostLike.postLike;
import static com.daily.daily.postcomment.domain.QPostComment.postComment;

@Repository
@RequiredArgsConstructor
public class PostRepositoryQuerydslImpl implements PostRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Post> findSlice(Pageable pageable) {
        List<Post> posts = queryFactory
                .selectFrom(post)
                .leftJoin(post.postWriter, member).fetchJoin()
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = posts.size() > pageable.getPageSize(); // 다음 페이지 여부 확인

        if (hasNext) {
            posts.remove(posts.size() - 1); // 마지막 항목 제거
        }

        return new SliceImpl<>(posts ,pageable, hasNext);
    }

    @Override
    public void deletePostAndRelatedEntities(Long postId) {
        queryFactory.delete(postLike)
                .where(postLike.post.id.eq(postId)).execute();
        queryFactory.delete(postHashtag)
                .where(postHashtag.post.id.eq(postId)).execute();
        queryFactory.delete(postComment)
                .where(postComment.post.id.eq(postId)).execute();
        queryFactory.delete(post)
                .where(post.id.eq(postId)).execute();
    }

    @Override
    public Slice<Post> findPostsByHashtag(List<String> hashtags, Pageable pageable) {
        List<Post> posts = queryFactory
                .selectFrom(post)
                .leftJoin(post.postWriter, member).fetchJoin()
                .innerJoin(post.postHashtags).fetchJoin()
                .where(post.postHashtags.any().hashtag.tagName.in(hashtags))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = posts.size() > pageable.getPageSize();

        if (hasNext) {
            posts.remove(posts.size() - 1); // 마지막 항목 제거
        }

        return new SliceImpl<>(posts, pageable, hasNext);
    }

    @Override
    public Slice<Post> findPostsByHashtag(Long memberId, Pageable pageable) {
        List<Post> posts = queryFactory
                .selectFrom(post)
                .leftJoin(post.postWriter, member).fetchJoin()
                .innerJoin(post.postHashtags).fetchJoin()
                .where(post.postWriter.id.in(memberId))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = posts.size() > pageable.getPageSize();

        if (hasNext) {
            posts.remove(posts.size() - 1); // 마지막 항목 제거
        }

        return new SliceImpl<>(posts, pageable, hasNext);
    }

}
