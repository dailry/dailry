package com.daily.daily.dailrypage.dto;

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
    private Long pageId;
    private String background;
    private String thumbnail;
    private int pageNumber;
    private Object elements;

    public static DailryPageDTO from(DailryPage dailryPage) {
        return DailryPageDTO.builder()
                .pageId(dailryPage.getId())
                .background(dailryPage.getBackground())
                .thumbnail(dailryPage.getThumbnail())
                .pageNumber(dailryPage.getPageNumber())
                .elements(dailryPage.getElements().values())
                .build();
    }

}
