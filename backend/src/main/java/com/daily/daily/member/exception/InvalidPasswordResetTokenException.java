package com.daily.daily.member.exception;

public class InvalidPasswordResetTokenException extends RuntimeException {
    public InvalidPasswordResetTokenException() {
        super("비밀번호 재설정 토큰이 유효하지 않습니다.");
    }
}
