package com.daily.daily.dailrypage.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class DailryPageThumbnailNotFoundException extends CustomException {
    public DailryPageThumbnailNotFoundException() {
        super(ErrorCode.DAILRY_THUMBNAIL_NOT_FOUND);
    }

}
