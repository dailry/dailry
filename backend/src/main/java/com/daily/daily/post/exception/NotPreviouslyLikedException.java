package com.daily.daily.post.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class NotPreviouslyLikedException extends CustomException {
    public NotPreviouslyLikedException() {
        super(ErrorCode.NOT_PREVIOUSLY_LIKE);
    }
}
