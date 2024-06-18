package com.daily.daily.common.exception;

public class FileUploadFailureException extends WrappingCheckedException {
    public FileUploadFailureException(Exception checkedException) {
        super(ErrorCode.FILE_UPLOAD_FAILURE, checkedException);
    }
}