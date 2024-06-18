package com.daily.daily.auth.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class LoginFailureException extends CustomException {
    public LoginFailureException() {
        super(ErrorCode.INVALID_LOGIN_INFO);
    }
}
