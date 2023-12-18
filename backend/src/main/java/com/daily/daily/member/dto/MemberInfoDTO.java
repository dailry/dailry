package com.daily.daily.member.dto;

import com.daily.daily.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberInfoDTO {
    private Long id;
    private String username;
    private String nickname;

    public static MemberInfoDTO from(Member member) {
        return new MemberInfoDTO(member.getId(), member.getUsername(), member.getNickname());
    }
}
