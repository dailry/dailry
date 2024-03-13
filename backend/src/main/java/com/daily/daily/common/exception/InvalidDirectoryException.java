package com.daily.daily.common.exception;

public class InvalidDirectoryException extends RuntimeException {
    public InvalidDirectoryException() {
        super("올바르지 않은 디렉토리 경로입니다");
    }
}
