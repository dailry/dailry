package com.daily.daily.member.fixture;

import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.domain.Member;
import com.daily.daily.oauth.constant.SocialType;

public class MemberFixture {
    public static Member 일반회원() {
        return Member.builder()
                .id(3L)
                .username("hello123")
                .nickname("일반회원임")
                .email("aokpasdf@gmail.com")
                .socialType(SocialType.NONE)
                .role(MemberRole.ROLE_MEMBER)
                .build();
    }

    public static Member 소셜회원() {
        return Member.builder()
                .id(5L)
                .username("socia123l")
                .nickname("소셜회원임")
                .email("aseokp@gmail.com")
                .socialType(SocialType.GOOGLE)
                .role(MemberRole.ROLE_MEMBER)
                .build();
    }
}
