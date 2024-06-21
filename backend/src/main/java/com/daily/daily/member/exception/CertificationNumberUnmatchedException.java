package com.daily.daily.member.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class CertificationNumberUnmatchedException extends CustomException {
    public CertificationNumberUnmatchedException() {
        super(ErrorCode.CERT_NUMBER_UNMATCHED);
    }
}
