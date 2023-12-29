package com.daily.daily.member.exception;

public class DuplicatedEmailException extends IllegalArgumentException{
    public DuplicatedEmailException() {
        super("이미 사용중인 이메일 입니다.");
    }
}
