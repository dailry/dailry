package com.daily.daily.dailrypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DailryPageThumbnailDTO {
    private Integer pageId;
    private Integer pageNumber;
    private String thumbnail;
}

