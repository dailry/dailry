package com.daily.daily.common.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponseDTO {
    private boolean isSuccessful = false;
    private String msg;
    private Integer statusCode;

    public ExceptionResponseDTO(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
