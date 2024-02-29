package com.daily.daily.testutil.generator;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.repository.DailryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailryGenerator {

    @Autowired
    DailryRepository dailryRepository;

    public Dailry generate() {
        return dailryRepository.save(Dailry.builder().build());
    }
}
