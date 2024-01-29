package com.daily.daily.dailry.repository;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.dto.DailryFindDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailryRepository extends JpaRepository<Dailry, Long> {
    List<DailryFindDTO> findIdsAndTitlesByMemberId(Long memberId);
}
