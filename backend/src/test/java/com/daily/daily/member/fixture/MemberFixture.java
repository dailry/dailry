package com.daily.daily.member.fixture;

import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.domain.Member;
import com.daily.daily.oauth.constant.SocialType;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

    public static Long 일반회원1_ID = 3L;
    private static String 일반회원1_유저네임 = "hello123";
    private static String 일반회원1_닉네임 = "일반회원임";
    private static String 일반회원1_이메일 = "aokpasdf@gmail.com";

    public static Long 일반회원2_ID = 13L;
    private static String 일반회원2_유저네임 = "general2";
    private static String 일반회원2_닉네임 = "일반회원2임";
    private static String 일반회원2_이메일 = "aqwesdf@gmail.com";

    public static Long 구글소셜회원_ID = 5L;
    private static String 구글소셜회원_유저네임 = "google0social123";
    private static String 구글소셜회원_닉네임 = "구글소셜회원임";
    private static String 구글소셜회원_이메일 = "googlesocial@gmail.com";

    public static Member 일반회원1() {
        Member 일반회원1 = Member.builder()
                .username(일반회원1_유저네임)
                .nickname(일반회원1_닉네임)
                .email(일반회원1_이메일)
                .socialType(SocialType.NONE)
                .role(MemberRole.ROLE_MEMBER)
                .build();

        ReflectionTestUtils.setField(일반회원1, "id", 일반회원1_ID);
        return 일반회원1;
    }

    public static Member 일반회원2() {
        Member 일반회원2 = Member.builder()
                .username(일반회원2_유저네임)
                .nickname(일반회원2_닉네임)
                .email(일반회원2_이메일)
                .socialType(SocialType.NONE)
                .role(MemberRole.ROLE_MEMBER)
                .build();

        ReflectionTestUtils.setField(일반회원2, "id", 일반회원2_ID);
        return 일반회원2;
    }


    public static Member 구글소셜회원() {
        Member 구글소셜회원 = Member.builder()
                .username(구글소셜회원_유저네임)
                .nickname(구글소셜회원_닉네임)
                .email(구글소셜회원_이메일)
                .socialType(SocialType.GOOGLE)
                .role(MemberRole.ROLE_MEMBER)
                .build();

        ReflectionTestUtils.setField(구글소셜회원, "id", 구글소셜회원_ID);
        return 구글소셜회원;
    }
}
