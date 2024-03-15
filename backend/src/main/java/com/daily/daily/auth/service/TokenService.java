package com.daily.daily.auth.service;

import com.daily.daily.auth.exception.TokenExpiredException;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.jwt.RefreshTokenRepository;
import com.daily.daily.common.service.CookieService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final CookieService cookieService;

    private static final String ACCESS_TOKEN = "AccessToken";

    public String renewToken(HttpServletResponse response, String accessToken, String refreshToken) {
        boolean isAccessTokenExpired = jwtUtil.isExpired(accessToken);
        boolean isRefreshTokenExpired = jwtUtil.isExpired(refreshToken);

        if (isAccessTokenExpired && isRefreshTokenExpired) {
            throw new TokenExpiredException();
        }

        if(isAccessTokenExpired) {
            String renewAccessToken = createAccessToken(refreshToken);
            ResponseCookie accessCookie = cookieService.createCookie(ACCESS_TOKEN, renewAccessToken);
            response.addHeader(SET_COOKIE, accessCookie.toString());
            return accessCookie.getValue();
        }
        return accessToken;
    }

    public String createAccessToken(final String refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Member member = memberRepository.findById(refreshToken1.getId())
                .orElseThrow(MemberNotFoundException::new);

        return jwtUtil.generateAccessToken(refreshToken1.getId(), member.getRole());
    }
}
