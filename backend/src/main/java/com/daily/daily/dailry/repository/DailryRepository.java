package com.daily.daily.dailry.repository;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.dto.DailryFindDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DailryRepository extends JpaRepository<Dailry, Long> {
    @Query("SELECT new com.daily.daily.dailry.dto.DailryFindDTO(d.id, d.title) FROM Dailry d WHERE d.member.id = :memberId")
    List<DailryFindDTO> findIdsAndTitlesByMemberId(Long memberId);
}
