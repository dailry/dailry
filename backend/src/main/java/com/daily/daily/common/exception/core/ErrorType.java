package com.daily.daily.common.exception.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    AUTH("인증/인가 관련 에러"),

    FILE("파일 관련 에러"),

    DAILRY("다일리 관련 에러"),

    MEMBER("회원 관련 에러"),

    COMMUNITY("커뮤니티 관련 에러");

    private final String description;
}
