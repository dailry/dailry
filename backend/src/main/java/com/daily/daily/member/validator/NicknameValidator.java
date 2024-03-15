package com.daily.daily.member.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {

    private static final Pattern pattern = Pattern.compile("^[가-힣a-zA-Z0-9]*$");
    private static final int MAX_SIZE = 30;
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 15;

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        if (nickname == null) return true;
        if (!pattern.matcher(nickname).matches()) {
            return false;
        }
        if (nickname.length() < MIN_LENGTH || nickname.length() > MAX_LENGTH) {
            return false;
        }
        if (!isProperSize(nickname)) {
            return false;
        }
        return true;
    }

    /**
     * 한글은 한 글자에 사이즈3, 영어와 숫자는 한 글자에 사이즈2로 책정할 때
     * MAX_SIZE(30)를 초과하면 false 를 반환한다.
     */
    private boolean isProperSize(String nickname) {
        int size = 0;
        for (char c : nickname.toCharArray()) {
            if (isAlphabeticOrNumber(c)) {
                size += 2;
                continue;
            }
            if (isHangeul(c)) {
                size += 3;
            }
        }

        return size <= MAX_SIZE;
    }

    private boolean isAlphabeticOrNumber(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }

    private boolean isHangeul(char c) {
        return !isAlphabeticOrNumber(c);
    }

}
