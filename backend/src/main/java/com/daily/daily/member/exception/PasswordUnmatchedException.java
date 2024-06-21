package com.daily.daily.member.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class PasswordUnmatchedException extends CustomException {
    public PasswordUnmatchedException() {
        super(ErrorCode.PASSWORD_UNMATCHED);
    }

}
