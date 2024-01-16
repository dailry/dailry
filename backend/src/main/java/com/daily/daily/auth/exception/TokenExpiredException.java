package com.daily.daily.auth.exception;

public class TokenExpiredException  extends RuntimeException {
    public TokenExpiredException() {
        super("모든 token이 만료되었습니다.");
    }
}
