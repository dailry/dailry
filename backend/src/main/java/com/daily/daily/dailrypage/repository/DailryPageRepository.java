package com.daily.daily.dailrypage.repository;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.dto.DailryPageThumbnailDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailryPageRepository extends JpaRepository<DailryPage, Long> {
    int countByDailry(Dailry dailry);
    List<DailryPageThumbnailDTO> findPageNumbersAndThumbnailsByDailryId(Long dailryId);
}
