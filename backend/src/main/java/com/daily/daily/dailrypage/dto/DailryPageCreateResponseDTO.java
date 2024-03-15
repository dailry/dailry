package com.daily.daily.dailrypage.dto;

import com.daily.daily.dailrypage.domain.DailryPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DailryPageCreateResponseDTO {
    private Long dailryId;
    private Long pageId;
    private String background;
    private String thumbnail;
    private Long pageNumber;
    public static DailryPageCreateResponseDTO from(DailryPage dailryPage) {
        return DailryPageCreateResponseDTO.builder()
                .dailryId(dailryPage.getDailry().getId())
                .pageId(dailryPage.getId())
                .background(dailryPage.getBackground())
                .thumbnail(dailryPage.getThumbnail())
                .pageNumber(Long.valueOf(dailryPage.getPageNumber()))
                .build();
    }
}
