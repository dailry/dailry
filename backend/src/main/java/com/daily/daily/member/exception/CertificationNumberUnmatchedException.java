package com.daily.daily.member.exception;

public class CertificationNumberUnmatchedException extends RuntimeException{
    public CertificationNumberUnmatchedException() {
        super("인증번호가 일치하지 않습니다.");
    }
}
