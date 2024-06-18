package com.daily.daily.common.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class FileContentTypeUnmatchedException extends CustomException {
    public FileContentTypeUnmatchedException() {
        super(ErrorCode.FILE_CONTENT_TYPE_UNMATCHED);
    }
}
