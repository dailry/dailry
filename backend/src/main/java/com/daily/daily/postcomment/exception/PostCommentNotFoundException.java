package com.daily.daily.postcomment.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class PostCommentNotFoundException extends CustomException {
    public PostCommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
