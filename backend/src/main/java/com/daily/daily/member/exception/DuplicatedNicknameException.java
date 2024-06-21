package com.daily.daily.member.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class DuplicatedNicknameException extends CustomException {
    public DuplicatedNicknameException() {
        super(ErrorCode.DUPLICATED_NICKNAME);
    }
}
