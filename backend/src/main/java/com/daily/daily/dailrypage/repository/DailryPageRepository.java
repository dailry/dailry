package com.daily.daily.dailrypage.repository;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.domain.DailryPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailryPageRepository extends JpaRepository<DailryPage, Long>, DailryPageQuerydsl {
    int countByDailry(Dailry dailry);

    void deleteByDailry_Id(Long dailryId);
}
