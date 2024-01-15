package com.daily.daily.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SuccessResponseDTO {
    private boolean isSuccessful = true;
    private Integer statusCode;

    public SuccessResponseDTO(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
