package com.daily.daily.auth.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class TokenExpiredException  extends CustomException {
    public TokenExpiredException() {
        super(ErrorCode.ALL_AUTH_TOKEN_EXPIRED);
    }
}
