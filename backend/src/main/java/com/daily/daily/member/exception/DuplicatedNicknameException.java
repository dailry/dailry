package com.daily.daily.member.exception;

public class DuplicatedNicknameException extends IllegalArgumentException{
    public DuplicatedNicknameException() {
        super("닉네임이 중복됩니다.");
    }
}
