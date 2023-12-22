package com.daily.daily.member.dto;


import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.validator.Nickname;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.regex.Matcher;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "특수문자를 제외한 문자만 입력하세요.")
    @Length(min = 4, max = 15)
    @NotBlank
    private String username;
    // 길이 제한(8~20)
    @Length(min = 8, max = 20)
    @NotBlank
    private String password;

    @Nickname
    private String nickname;

    public Member toMember() {
        return Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .role(MemberRole.ROLE_MEMBER)
                .build();
    }
}

