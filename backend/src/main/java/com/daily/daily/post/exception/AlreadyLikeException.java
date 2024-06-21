package com.daily.daily.post.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class AlreadyLikeException extends CustomException {
    public AlreadyLikeException() {
        super(ErrorCode.ALRADY_LIKE_POST);
    }
}
