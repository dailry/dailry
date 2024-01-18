package com.daily.daily.dailry.service;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.dto.DailryUpdateDTO;
import com.daily.daily.dailry.repository.DailryRepository;
import com.daily.daily.dailrypage.exception.DailryPageNotFoundException;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public DailryDTO update(Long dairlyId, DailryUpdateDTO dailryUpdateDTO) {
        Dailry dailry = dailryRepository.findById(dairlyId)
                .orElseThrow(DailryPageNotFoundException::new);

        dailry.updateTitle(dailryUpdateDTO.getTitle());

        return DailryDTO.from(dailry);
    }

    public DailryDTO find(Long dairlyId) {
        Dailry dailry = dailryRepository.findById(dairlyId)
                .orElseThrow(DailryPageNotFoundException::new);

        return DailryDTO.from(dailry);
    }
//
//    public void delete(Long pageId) {
//        dailryPageRepository.deleteById(pageId);
//    }
}
