package com.daily.daily.post.repository;

import com.daily.daily.post.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daily.daily.member.domain.QMember.member;
import static com.daily.daily.post.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepositoryQuerydslImpl implements PostRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    @Override
    public Slice<Post> find(Pageable pageable) {
        List<Long> likeCounts = getLikeCounts(pageable);
        List<Post> posts = queryFactory
                .selectFrom(post)
                .leftJoin(post.postWriter, member).fetchJoin()
                .orderBy(post.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = posts.size() > pageable.getPageSize(); // 다음 페이지 여부 확인

        if (hasNext) {
            posts.remove(posts.size() - 1); // 마지막 항목 제거
        }

        for (int i = 0; i < posts.size(); i++) {
            posts.get(i).setLikeCount(likeCounts.get(i));
        }

        System.out.println("querydsl test");

        return new SliceImpl<>(posts ,pageable, hasNext);
    }

    private List<Long> getLikeCounts(Pageable pageable) {
        return em.createNativeQuery(
                        "SELECT COUNT(postlike.id) " +
                                "FROM post " +
                                "LEFT JOIN postlike ON post.id = postlike.post_id " +
                                "GROUP BY post.id " +
                                "ORDER BY post.id ASC " +
                                "LIMIT :pageSize OFFSET :offset", Long.class)
                .setParameter("pageSize", pageable.getPageSize())
                .setParameter("offset", pageable.getOffset())
                .getResultList();
    }
}
