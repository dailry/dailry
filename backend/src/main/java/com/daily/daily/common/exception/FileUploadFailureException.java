package com.daily.daily.common.exception;

import com.daily.daily.common.exception.core.ErrorCode;
import com.daily.daily.common.exception.core.WrappingCheckedException;

public class FileUploadFailureException extends WrappingCheckedException {
    public FileUploadFailureException(Exception checkedException) {
        super(ErrorCode.FILE_UPLOAD_FAILURE, checkedException);
    }
}