package com.daily.daily.member.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface Nickname {
    String message()default "올바르지 않은 닉네임 형식입니다.";
    Class[] groups() default {};
    Class[] payload() default {};

}
