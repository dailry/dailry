package com.daily.daily.dailrypage.service;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.exception.DailryNotFoundException;
import com.daily.daily.dailry.repository.DailryRepository;
import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.dto.DailryPageDTO;
import com.daily.daily.dailrypage.dto.DailryPageFindDTO;
import com.daily.daily.dailrypage.dto.DailryPageUpdateDTO;
import com.daily.daily.dailrypage.exception.DailryPageNotFoundException;
import com.daily.daily.dailrypage.repository.DailryPageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DailryPageService {

    private final DailryPageRepository dailryPageRepository;
    private final DailryRepository dailryRepository;

    public DailryPageDTO create() {
        DailryPage savedPage = dailryPageRepository.save(DailryPage.createEmptyPage());

        return DailryPageDTO.from(savedPage);
    }

    public DailryPageDTO create2(Long dailryId) {
        Dailry dailry = dailryRepository.findById(dailryId)
                .orElseThrow(DailryNotFoundException::new);

        int dailryPageCount = dailryPageRepository.countByDailry(dailry);
        int newDiaryPageNumber = dailryPageCount + 1;

        DailryPage dailryPage = DailryPage.builder()
                .dailry(dailry)
                .pageNumber(newDiaryPageNumber)
                .build();

        DailryPage savedPage = dailryPageRepository.save(dailryPage);

        return DailryPageDTO.from(savedPage);
    }

    public DailryPageDTO update(Long pageId, DailryPageUpdateDTO dailryPageUpdateDTO) {
        log.info("DailryPageService.update() 호출");

        DailryPage findPage = dailryPageRepository.findById(pageId)
                .orElseThrow(DailryPageNotFoundException::new);

        findPage.updateBackground(dailryPageUpdateDTO.getBackground());
        findPage.updateElements(dailryPageUpdateDTO.getElements());

        return DailryPageDTO.from(findPage);
    }

    public DailryPageDTO find(Long pageId) {
        DailryPage findPage = dailryPageRepository.findById(pageId)
                .orElseThrow(DailryPageNotFoundException::new);

        return DailryPageDTO.from(findPage);
    }

    public List<DailryPageFindDTO> findAll(Long dailryId) {
        return dailryPageRepository.findIdsAndThumbnailsByDailryId(dailryId);
    }

    public void delete(Long pageId) {
        dailryPageRepository.deleteById(pageId);
    }
}
