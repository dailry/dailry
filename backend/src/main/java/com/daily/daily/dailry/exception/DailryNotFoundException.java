package com.daily.daily.dailry.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class DailryNotFoundException extends CustomException {
        public DailryNotFoundException() {
            super(ErrorCode.DAILRY_NOT_FOUND);
        }
}
