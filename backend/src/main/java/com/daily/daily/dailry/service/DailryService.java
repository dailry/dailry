package com.daily.daily.dailry.service;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.repository.DailryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DailryService {

    private final DailryRepository dailryRepository;

    public DailryDTO create() {
        Dailry savedDailry = dailryRepository.save(Dailry.createEmptyDailry());
        return DailryDTO.from(savedDailry);
    }
}
