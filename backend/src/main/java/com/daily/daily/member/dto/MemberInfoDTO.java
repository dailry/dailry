package com.daily.daily.member.dto;

import com.daily.daily.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MemberInfoDTO {
    private Long id;
    private String username;
    private String nickname;
    private String email;

    public static MemberInfoDTO from(Member member) {
        return new MemberInfoDTO(member.getId(), member.getUsername(), member.getNickname(), member.getEmail());
    }
}
