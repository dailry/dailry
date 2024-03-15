package com.daily.daily.auth.dto;

import com.daily.daily.member.constant.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JwtClaimDTO {
    private Long memberId;
    private MemberRole role;
}
