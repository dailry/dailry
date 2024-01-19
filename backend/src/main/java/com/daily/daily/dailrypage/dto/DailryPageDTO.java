package com.daily.daily.dailrypage.dto;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.domain.DailryPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class DailryPageDTO {
    private Long id;
    private String background;
    private Object elements;

    public static DailryPageDTO from(DailryPage dailryPage) {
        return DailryPageDTO.builder()
                .id(dailryPage.getId())
                .background(dailryPage.getBackground())
                .elements(dailryPage.getElements())
                .build();
    }

}
