package com.daily.daily.dailry.dto;

import com.daily.daily.dailry.domain.Dailry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DailryDTO {

    private Long id;
    private String title;

    public static DailryDTO from(Dailry dailry) {
        return DailryDTO.builder()
                .id(dailry.getId())
                .title(dailry.getTitle())
                .build();
    }
}
