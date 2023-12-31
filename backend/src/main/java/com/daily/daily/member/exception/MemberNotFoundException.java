package com.daily.daily.member.exception;

import java.util.NoSuchElementException;

public class MemberNotFoundException extends NoSuchElementException {

    public MemberNotFoundException() {
        super("해당 회원을 찾을 수 없습니다.");
    }
}
