package com.daily.daily.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;

    public static TokenDTO of(String accessToken, String refreshToken) {
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
