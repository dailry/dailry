package com.daily.daily.post.repository;

import com.daily.daily.post.domain.HotPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daily.daily.member.domain.QMember.member;
import static com.daily.daily.post.domain.QHotPost.hotPost;
import static com.daily.daily.post.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class HotPostRepositoryQuerydslImpl implements HotPostRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<HotPost> findSlice(Pageable pageable) {
        List<HotPost> hotPosts = queryFactory.selectFrom(hotPost)
                .leftJoin(hotPost.post, post).fetchJoin()
                .leftJoin(hotPost.post.postWriter, member).fetchJoin()
                .orderBy(hotPost.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = hotPosts.size() > pageable.getPageSize();

        if (hasNext) {
            hotPosts.remove(hotPosts.size() - 1);
        }

        return new SliceImpl<>(hotPosts, pageable, hasNext);
    }
}
