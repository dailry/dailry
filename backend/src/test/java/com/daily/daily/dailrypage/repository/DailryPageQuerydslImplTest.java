package com.daily.daily.dailrypage.repository;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.dto.DailryPageThumbnailDTO;
import com.daily.daily.testutil.config.JpaTest;
import com.daily.daily.testutil.generator.DailryGenerator;
import com.daily.daily.testutil.generator.DailryPageGenerator;
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
}