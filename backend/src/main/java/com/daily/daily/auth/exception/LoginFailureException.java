package com.daily.daily.auth.exception;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException() {
        super("로그인 정보가 올바르지 않습니다.");
    }
}
