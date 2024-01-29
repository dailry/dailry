package com.daily.daily.dailry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DailryFindDTO {
    private Long dailryId;
    private String title;
}
