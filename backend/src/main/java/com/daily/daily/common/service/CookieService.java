package com.daily.daily.common.service;

import jakarta.servlet.http.Cookie;
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

    private static final String ACCESS_TOKEN = "AccessToken";
    private static final String REFRESH_TOKEN = "RefreshToken";

    @Value("${app.properties.mainDomain}")
    private String mainDomain;

    public void setTokensInCookie(HttpServletResponse response, String accessToken, String refreshToken) {
        ResponseCookie accessCookie = createTokenCookie(ACCESS_TOKEN, accessToken);
        ResponseCookie refreshCookie = createTokenCookie(REFRESH_TOKEN, refreshToken);
        response.addHeader(SET_COOKIE, accessCookie.toString());
        response.addHeader(SET_COOKIE, refreshCookie.toString());
    }

    public ResponseCookie createTokenCookie(String cookieName, String token) {
        return ResponseCookie.from(cookieName, token)
                .domain(mainDomain)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .sameSite("None")
                .build();
    }

    public void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setDomain(mainDomain);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
