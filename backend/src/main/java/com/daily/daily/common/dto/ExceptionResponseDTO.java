package com.daily.daily.common.dto;

import com.daily.daily.common.exception.ErrorCode;
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

    public ExceptionResponseDTO(ErrorCode errorCode) {
        this.msg = errorCode.name();
        this.errorCode = errorCode.name();
    }


}
