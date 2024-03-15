package com.daily.daily.dailrypage.repository;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.dto.DailryPageThumbnailDTO;
import com.daily.daily.testutil.config.JpaTest;
import com.daily.daily.testutil.generator.DailryGenerator;
import com.daily.daily.testutil.generator.DailryPageGenerator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DailryPageQuerydslImplTest extends JpaTest {

    @Autowired
    DailryPageRepository dailryPageRepository;

    @Autowired
    DailryGenerator dailryGenerator;

    @Autowired
    DailryPageGenerator dailryPageGenerator;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("다일리 썸네일 조회 메서드를 테스트한다.")
    void findThumbnails() {
        //given
        Dailry dailry = dailryGenerator.generate();
        Dailry otherDailry = dailryGenerator.generate();

        dailryPageGenerator.generate(dailry, 3); // (dailry - 페이지수 3개, otherDailry - 페이지 수 0개)

        //when
        List<DailryPageThumbnailDTO> thumbnails = dailryPageRepository.findThumbnails(dailry.getId());
        List<DailryPageThumbnailDTO> otherThumbnails = dailryPageRepository.findThumbnails(otherDailry.getId());

        //then
        assertThat(thumbnails).hasSize(3);
        assertThat(otherThumbnails).isEmpty();
    }

    @Test
    @DisplayName("다일리 페이지 삭제 후, 삭제된 페이지의 pageNumber 보다 pageNumber 값이 큰 페이지들은, pageNumber가 1씩 당겨져야한다.")
    void deleteAndAdjustPageNumber() {
        //given
        Dailry dailry = dailryGenerator.generate();
        List<DailryPage> dailryPages = dailryPageGenerator.generate(dailry, 10); // 테스트용 다일리 페이지 10개

        DailryPage firstPage = dailryPages.get(0);
        //when
        dailryPageRepository.deleteAndAdjustPageNumber(firstPage);
        em.flush();
        em.clear();

        //then
        List<DailryPage> updatedDailryPages = em.createQuery("select dp from DailryPage dp where dp.dailry.id = :dailryId",
                        DailryPage.class)
                .setParameter("dailryId", dailry.getId())
                .getResultList();

        assertThat(updatedDailryPages).hasSize(9); // 페이지가 삭제되었는지 확인
        int pageNumber = 1;
        for (DailryPage page : updatedDailryPages) {
            assertThat(page.getPageNumber()).isEqualTo(pageNumber++); // 페이지 넘버가 당겨졌는지 확인
        }
    }
}