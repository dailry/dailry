package com.daily.daily.dailrypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DailryPageThumbnailDTO {
    //TODO: pageId 추가 필요
    private Integer pageId;
    private int pageNumber;
    private String thumbnail;
}

