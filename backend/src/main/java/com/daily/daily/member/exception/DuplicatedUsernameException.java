package com.daily.daily.member.exception;

public class DuplicatedUsernameException extends IllegalArgumentException {
    public DuplicatedUsernameException() {
        super("로그인 ID가 중복됩니다.");
    }
}
