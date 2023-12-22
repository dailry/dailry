package com.daily.daily.member.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NicknameValidatorTest {

    NicknameValidator nicknameValidator = new NicknameValidator();
    @Test
    @DisplayName("한글, 숫자, 영어이외의 문자를 사용하여 닉네임으로 만들수 없다.")
    void isValid1() {
        boolean isInvalid1 = nicknameValidator.isValid("안 녕", null);
        boolean isInvalid2 = nicknameValidator.isValid("!!", null);

        assertThat(isInvalid1).isFalse();
        assertThat(isInvalid2).isFalse();
    }
    @Test
    @DisplayName("한글은 한 글자에 사이즈3, 영어와 숫자는 한 글자에 사이즈2로 책정할 때, 닉네임의 총 사이즈가 30을 초과하면 유효하지 않다")
    void isValid2() {
        boolean isInvalid1 = nicknameValidator.isValid("열한글자인닉네임이에요", null);
        boolean isInvalid2 = nicknameValidator.isValid("aaaaabbbbb333334", null);// 16글자 -> 2 * 16 = 32
        boolean isInvalid3 = nicknameValidator.isValid("한글여섯글자abcd123", null);// 3*6 + 2*7 = 32 -> 예외발생.

        assertThat(isInvalid1).isFalse();
        assertThat(isInvalid2).isFalse();
        assertThat(isInvalid3).isFalse();
    }

    @Test
    @DisplayName("한글 영어 숫자 상관없이 닉네임의 길이는 2를 넘어야한다.")
    void isValid3() {
        boolean isInvalid1 = nicknameValidator.isValid("아", null);
        boolean isInvalid2 = nicknameValidator.isValid("3", null);

        assertThat(isInvalid1).isFalse();
        assertThat(isInvalid2).isFalse();
    }

    @Test
    @DisplayName("위의 예외케이스가 아닌 성공적인 닉네임 케이스들을 테스트한다.")
    void isValid5() {
        boolean isValid = nicknameValidator.isValid("박건우123", null);
        boolean isValid2 = nicknameValidator.isValid("geonwoo123", null);
        boolean isValid3 = nicknameValidator.isValid("한글열글자닉네임", null);
        boolean isValid4 = nicknameValidator.isValid("aaaaabbbbb55555", null); // 영어,숫자 15글자 => Size : 30 (2 * 15)

        assertThat(isValid).isTrue();
        assertThat(isValid2).isTrue();
        assertThat(isValid3).isTrue();
        assertThat(isValid4).isTrue();
    }



}