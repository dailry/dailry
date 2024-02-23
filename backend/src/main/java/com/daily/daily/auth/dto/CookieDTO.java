package com.daily.daily.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookieDTO {
    private ResponseCookie accessCookie;
    private ResponseCookie refreshCookie;
}
