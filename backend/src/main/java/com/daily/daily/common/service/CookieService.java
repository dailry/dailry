package com.daily.daily.common.service;

import com.daily.daily.auth.dto.CookieDTO;
import com.daily.daily.auth.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CookieService {
    @Value("${app.properties.mainDomain}")
    private String mainDomain;

    @Value("${app.properties.cookie.sameSite}")
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
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setDomain(mainDomain);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
