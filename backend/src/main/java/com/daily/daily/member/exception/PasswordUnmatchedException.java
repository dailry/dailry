package com.daily.daily.member.exception;

public class PasswordUnmatchedException extends IllegalArgumentException{
    public PasswordUnmatchedException() {
        super("비밀번호가 일치하지 않습니다.");
    }

}
