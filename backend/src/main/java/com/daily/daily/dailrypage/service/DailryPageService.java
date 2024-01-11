package com.daily.daily.dailrypage.service;

import com.daily.daily.common.dto.CommonResponseDTO;
import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.dto.DailryPageDTO;
import com.daily.daily.dailrypage.dto.DailryPageUpdateDTO;
import com.daily.daily.dailrypage.exception.DailryPageNotFoundException;
import com.daily.daily.dailrypage.repository.DailryPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DailryPageService {

    private final DailryPageRepository dailryPageRepository;

    public DailryPageDTO create() {
        DailryPage savedPage = dailryPageRepository.save(DailryPage.createEmptyPage());

        return DailryPageDTO.from(savedPage);
    }

    public DailryPageDTO update(Long pageId, DailryPageUpdateDTO dailryPageUpdateDTO) {
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

    public void delete(Long pageId) {
        dailryPageRepository.deleteById(pageId);
    }
}
