package com.daily.daily.member.exception;

public class DuplicatedNicknameException extends IllegalArgumentException{
    public DuplicatedNicknameException() {
        super("이미 사용중인 닉네임입니다.");
    }
}
