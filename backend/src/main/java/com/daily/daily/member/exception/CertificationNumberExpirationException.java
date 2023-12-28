package com.daily.daily.member.exception;

public class CertificationNumberExpirationException extends RuntimeException{
    public CertificationNumberExpirationException() {
        super("인증번호가 만료되었습니다.");
    }
}
