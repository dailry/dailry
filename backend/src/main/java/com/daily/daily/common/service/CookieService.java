package com.daily.daily.common.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Service
@RequiredArgsConstructor
@Slf4j
public class CookieService {
    @Value("${app.properties.mainDomain}")
    private String mainDomain;

    @Value("${app.properties.cookie.sameSite: Strict}")
    private String sameSite;

    public ResponseCookie createCookie(String cookieName, String cookieValue) {
        return ResponseCookie.from(cookieName, cookieValue)
                .domain(mainDomain)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .sameSite(sameSite)
                .build();
    }

    public void deleteCookie(HttpServletResponse response, String cookieName) {
        ResponseCookie deleteCookie = ResponseCookie.from(cookieName, "")
                .domain(mainDomain)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .sameSite(sameSite)
                .build();

        response.setHeader(SET_COOKIE, deleteCookie.toString());
    }
}
