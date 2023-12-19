package com.daily.daily.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponseDTO {
    private String msg;
    private Integer statusCode;
}
