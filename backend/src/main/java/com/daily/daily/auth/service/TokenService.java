package com.daily.daily.auth.service;

import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.jwt.RefreshTokenRepository;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public void renewToken(HttpServletResponse response, String accessToken, RefreshToken refreshToken) {
        boolean isAccessTokenExpired = jwtUtil.isExpired(accessToken);
        boolean isRefreshTokenExpired = jwtUtil.isExpired(refreshToken.getRefreshToken());

        if (isAccessTokenExpired && isRefreshTokenExpired) {
            System.out.println("Access 토큰과 Refresh 토큰이 모두 만료되었습니다. 재로그인이 필요합니다.");
            return;
        }

        if(isAccessTokenExpired) {
            System.out.println("Access 토큰은 만료되었지만 Refresh 토큰은 유효합니다. Access 토큰을 재발급합니다.");
            String renewAccessToken = createAccessToken(refreshToken);
            UpdateCookie(response, "AccessToken", renewAccessToken);
            return;
        }

        System.out.println("Access 토큰과 Refresh 토큰이 모두 유효합니다.");
    }

    private void UpdateCookie(HttpServletResponse response, String cookieName, String renewAccessToken) {
        Cookie newAccessTokenCookie  = new Cookie(cookieName, renewAccessToken);
        newAccessTokenCookie.setPath("/");
        newAccessTokenCookie.setSecure(true);
        newAccessTokenCookie.setHttpOnly(true);
        response.addCookie(newAccessTokenCookie);
    }

    public String createAccessToken(final RefreshToken refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findById(refreshToken.getRefreshToken())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Member member = memberRepository.findById(refreshToken1.getId())
                .orElseThrow(MemberNotFoundException::new);

        return jwtUtil.generateAccessToken(refreshToken1.getId(), member.getRole());
    }
}
