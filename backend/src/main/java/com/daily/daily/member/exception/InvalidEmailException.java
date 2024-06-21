package com.daily.daily.member.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class InvalidEmailException extends CustomException {
    public InvalidEmailException() {
        super(ErrorCode.INVALID_EMAIL);
    }

}
