package com.daily.daily.member.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {
        super("등록되지 않은 이메일 입니다.");
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
