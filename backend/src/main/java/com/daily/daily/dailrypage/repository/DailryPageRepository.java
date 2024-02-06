package com.daily.daily.dailrypage.repository;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.dto.DailryPageThumbnailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DailryPageRepository extends JpaRepository<DailryPage, Long>, DailryPageQuerydsl {
    int countByDailry(Dailry dailry);
}
