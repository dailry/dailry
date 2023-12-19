package com.daily.daily.member.exception;

public class DuplicatedUsernameException extends IllegalArgumentException {
    public DuplicatedUsernameException() {
        super("이미 사용중인 아이디입니다.");
    }
}
