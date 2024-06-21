package com.daily.daily.member.exception;

import com.daily.daily.common.exception.core.CustomException;
import com.daily.daily.common.exception.core.ErrorCode;

public class CertificationNumberExpirationException extends CustomException {
    public CertificationNumberExpirationException() {
        super(ErrorCode.CERT_NUMBER_EXPIRED);
    }
}
