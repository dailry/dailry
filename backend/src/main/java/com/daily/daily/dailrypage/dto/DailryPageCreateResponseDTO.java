package com.daily.daily.dailrypage.dto;

import com.daily.daily.dailrypage.domain.DailryPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DailryPageCreateResponseDTO {
    private Long dailryPageId;
    private String background;
    private Long pageNumber;
    public static DailryPageCreateResponseDTO from(DailryPage dailryPage) {
        return DailryPageCreateResponseDTO.builder()
                .dailryPageId(dailryPage.getId())
                .background(dailryPage.getBackground())
                .build();
    }
}
