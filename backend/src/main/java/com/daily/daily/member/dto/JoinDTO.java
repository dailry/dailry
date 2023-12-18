package com.daily.daily.member.dto;


import com.daily.daily.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.regex.Matcher;

@Getter
@Setter
public class JoinDTO {
    private static final java.util.regex.Pattern NICKNAME_PATTERN = java.util.regex.Pattern.compile("^[가-힣a-zA-Z0-9]*$");

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "특수문자를 제외한 문자만 입력하세요.")
    @Length(min = 4, max = 15)
    @NotBlank
    private String username;
    // 길이 제한(8~20)
    @Length(min = 8, max = 20)
    @NotBlank
    private String password;

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "한글, 영어 대소문자, 숫자만 사용할 수 있습니다.")
    private String nickname;
    // 영어만 or 숫자만 2~20
    // 한글만 1~10
    // 혼합 2~20(한글은 최대 10자리까지)

    // 영어,숫자 2바이트 처리, 한글 3바이트 처리해서 30바이트
    // 리팩토링 필수
    public void setNickname(String nickname) {
        if (nickname == null) return;
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바르지 않은 형식입니다");
        }

        final int maxSize = 30;
        int size = 0;

        for (char c : nickname.toCharArray()) {
            if (isAlphabeticOrNumber(c)) {
                size += 2;
            }
            else {
                size += 3;
            }
        }

        if (size > maxSize) {
            throw new IllegalArgumentException("닉네임이 길이제한을 초과합니다.");
        }

        this.nickname = nickname;
    }
    private boolean isAlphabeticOrNumber(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }

    public Member toMember() {
        return Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();
    }
}

