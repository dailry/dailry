package com.daily.daily.dailrypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailryPagePreviewDTO {
    private Long dailryId;
    private List<DailryPageThumbnailDTO> pages;

    public static DailryPagePreviewDTO from(Long dailryId, List<DailryPageThumbnailDTO> thumbnails) {
        return new DailryPagePreviewDTO(dailryId, thumbnails);
    }
}
