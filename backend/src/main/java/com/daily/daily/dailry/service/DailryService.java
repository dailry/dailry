package com.daily.daily.dailry.service;

import com.daily.daily.common.exception.UnauthorizedAccessException;
import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.dto.DailryFindDTO;
import com.daily.daily.dailry.dto.DailryUpdateDTO;
import com.daily.daily.dailry.exception.DailryNotFoundException;
import com.daily.daily.dailry.repository.DailryRepository;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DailryService {

    private final DailryRepository dailryRepository;
    private final MemberRepository memberRepository;

    public DailryDTO create(Long memberId, DailryUpdateDTO dailryUpdateDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        String title = dailryUpdateDTO.getTitle();

        Dailry dailry = Dailry.builder()
                .title(title)
                .member(member)
                .build();

        Dailry savedDailry = dailryRepository.save(dailry);
        return DailryDTO.from(savedDailry);
    }

    public DailryDTO update(Long memberId, Long dairlyId, DailryUpdateDTO dailryUpdateDTO) {
        Dailry dailry = dailryRepository.findById(dairlyId)
                .orElseThrow(DailryNotFoundException::new);

        if (!dailry.belongsTo(memberId)) {
            throw new UnauthorizedAccessException();
        }

        dailry.updateTitle(dailryUpdateDTO.getTitle());

        return DailryDTO.from(dailry);
    }

    public DailryDTO find(Long memberId, Long dairlyId) {
        Dailry dailry = dailryRepository.findById(dairlyId)
                .orElseThrow(DailryNotFoundException::new);

        if (!dailry.belongsTo(memberId)) {
            throw new UnauthorizedAccessException();
        }
        return DailryDTO.from(dailry);
    }

    public List<DailryFindDTO> findAll(Long memberId) {
        return dailryRepository.findIdsAndTitlesByMemberId(memberId);
    }

    public void delete(Long memberId, Long dairlyId) {
        Dailry dailry = dailryRepository.findById(dairlyId)
                .orElseThrow(DailryNotFoundException::new);
        if (!dailry.belongsTo(memberId)) {
            throw new UnauthorizedAccessException();
        }
        dailryRepository.deleteById(dairlyId);
    }

}
