package com.daily.daily.common.exception.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Checked Exception을 Wrapping 해야할 때 사용합니다.
 *
 * ExceptionHandler를 통해 에러에 대한 API 응답을 내려주고,
 * log로 실제 발생한 에러가 어떤것인지 확인해야 할 때 사용됩니다. Ex) FileUploadFailureException
 */

@Slf4j
@Getter
public class WrappingCheckedException extends CustomException {
    private final Exception checkedException;
    public WrappingCheckedException(ErrorCode errorCode, Exception checkedException) {
        super(errorCode);
        this.checkedException = checkedException;

        log.error(checkedException.getMessage(), checkedException);
    }
}
