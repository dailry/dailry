package com.daily.daily.dailrypage.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class DailryPageNotFoundException extends CustomException {
    public DailryPageNotFoundException() {
        super(ErrorCode.DAILRY_PAGE_NOT_FOUND);
    }

}
