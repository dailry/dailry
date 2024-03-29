package com.daily.daily.testutil.generator;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.repository.DailryPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DailryPageGenerator {

    @Autowired
    DailryPageRepository dailryPageRepository;

    public List<DailryPage> generate(Dailry dailry, int count) {
        List<DailryPage> result = new ArrayList<>();
        
        for (int i = 1; i <= count; i++) {
            DailryPage dailryPage = DailryPage.builder()
                    .dailry(dailry)
                    .pageNumber(i)
                    .build();

            result.add(dailryPageRepository.save(dailryPage));
        }

        return result;
    }
}
