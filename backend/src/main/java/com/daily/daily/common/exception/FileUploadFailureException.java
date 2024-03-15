package com.daily.daily.common.exception;

import java.io.IOException;

public class FileUploadFailureException extends RuntimeException {
    private final Exception originalException;

    public FileUploadFailureException(IOException e) {
        super("파일 업로드에 실패하였습니다.");
        originalException = e;
    }

    public Exception getOriginalException() {
        return originalException;
    }
}
