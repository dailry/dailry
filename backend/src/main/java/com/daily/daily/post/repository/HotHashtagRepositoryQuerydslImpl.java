package com.daily.daily.post.repository;

import com.daily.daily.post.domain.HotHashtag;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.daily.daily.post.domain.QPost.post;
import static com.daily.daily.post.domain.QPostHashtag.postHashtag;

@Repository
@RequiredArgsConstructor
public class HotHashtagRepositoryQuerydslImpl implements HotHashtagRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<HotHashtag> findHotHashTags() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.minusHours(1).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(1);

        List<Tuple> result = queryFactory
                .select(postHashtag.hashtag, postHashtag.count())
                .from(post)
                .join(post.postHashtags, postHashtag)
                .where(post.createdTime.between(startTime, endTime))
                .groupBy(postHashtag.hashtag)
                .orderBy(postHashtag.count().desc())
                .limit(3)
                .fetch();

        System.out.println("query: " + postHashtag);
        System.out.println("query: " + postHashtag.count().longValue());

        return result.stream()
                .map(tuple -> HotHashtag.of(tuple.get(postHashtag.count()), tuple.get(postHashtag.hashtag)))
                .collect(Collectors.toList());
    }
}
