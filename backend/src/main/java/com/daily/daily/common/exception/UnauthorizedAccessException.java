package com.daily.daily.common.exception;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException() {
        super("접근할 권한이 없습니다.");
    }
}
