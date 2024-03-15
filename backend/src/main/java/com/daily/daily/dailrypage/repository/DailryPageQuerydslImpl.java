package com.daily.daily.dailrypage.repository;

import com.daily.daily.dailrypage.domain.DailryPage;
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
    @Override
    public void deleteAndAdjustPageNumber(DailryPage page) {
        queryFactory.delete(dailryPage)
                .where(dailryPage.id.eq(page.getId()))
                .execute();

        adjustPageNumber(page.getDailryId(), page.getPageNumber());
    }

    private void adjustPageNumber(Long dailryId, Integer pageNumberOfDeletedPage) {
        queryFactory.update(dailryPage)
                .set(dailryPage.pageNumber, dailryPage.pageNumber.subtract(1))
                .where(dailryPage.dailry.id.eq(dailryId)
                        .and(dailryPage.pageNumber.gt(pageNumberOfDeletedPage))
                ).execute();
    }
}
