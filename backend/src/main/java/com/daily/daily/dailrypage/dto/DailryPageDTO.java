package com.daily.daily.dailrypage.dto;

import com.daily.daily.dailrypage.domain.DailryPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class DailryPageDTO {
    private Long pageId;
    private String background;
    private String thumbnail;
    private int pageNumber;
    private Collection<Object> elements;

    public static DailryPageDTO from(DailryPage dailryPage) {
        Collection<Object> elements = dailryPage.getElements().values();

        return DailryPageDTO.builder()
                .pageId(dailryPage.getId())
                .background(dailryPage.getBackground())
                .thumbnail(dailryPage.getThumbnail())
                .pageNumber(dailryPage.getPageNumber())
                .elements(elements)
                .build();
    }

}
