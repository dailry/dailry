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

    private Long id;
    private String title;
    private Member member;

    public static DailryDTO from(Dailry dailry) {
        return DailryDTO.builder()
                .id(dailry.getId())
                .title(dailry.getTitle())
                .member(dailry.getMember())
                .build();
    }
}
