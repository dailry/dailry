package com.daily.daily.dailry.dto;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DailryDTO {

    private Long dailryId;
    private String title;

    public static DailryDTO from(Dailry dailry) {
        return DailryDTO.builder()
                .dailryId(dailry.getId())
                .title(dailry.getTitle())
                .build();
    }
}
