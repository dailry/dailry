package com.daily.daily.common.dto;

import com.daily.daily.common.exception.core.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponseDTO {
    private boolean isSuccessful = false;
    private Integer statusCode;
    private String errorCode;
    private String msg;


    public ExceptionResponseDTO(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    private ExceptionResponseDTO(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
        this.errorCode = errorCode.name();
        this.msg = errorCode.getMsg();
    }
    public static ExceptionResponseDTO of(ErrorCode errorCode) {
        return new ExceptionResponseDTO(errorCode);
    }

}
