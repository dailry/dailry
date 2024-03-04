package com.daily.daily.dailrypage.repository;

import com.daily.daily.dailrypage.domain.QDailryPage;
import com.daily.daily.dailrypage.dto.DailryPageThumbnailDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daily.daily.dailrypage.domain.QDailryPage.dailryPage;

@Repository
@RequiredArgsConstructor
public class DailryPageQuerydslImpl implements DailryPageQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailryPageThumbnailDTO> findThumbnails(Long dailryId) {
        return queryFactory.select(Projections.bean(DailryPageThumbnailDTO.class,
                        dailryPage.id.as("pageId"),
                        dailryPage.pageNumber,
                        dailryPage.thumbnail))
                .from(dailryPage)
                .where(dailryPage.dailry.id.eq(dailryId))
                .fetch();
    }
}
