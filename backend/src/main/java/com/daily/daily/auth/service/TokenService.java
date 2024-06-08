package com.daily.daily.auth.service;

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

import static com.daily.daily.auth.jwt.JwtUtil.ACCESS_TOKEN;
import static com.daily.daily.auth.jwt.JwtUtil.REFRESH_TOKEN;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final CookieService cookieService;


    public String renewToken(HttpServletResponse response, String accessToken, String refreshToken) {
        boolean isAccessTokenExpired = jwtUtil.isExpired(accessToken);
        boolean isRefreshTokenExpired = jwtUtil.isExpired(refreshToken);

        if (isAccessTokenExpired && isRefreshTokenExpired) {
            cookieService.deleteCookie(response, ACCESS_TOKEN);
            cookieService.deleteCookie(response, REFRESH_TOKEN);
            deleteRefreshToken(refreshToken);
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
        RefreshToken findRefreshToken = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Member member = memberRepository.findById(findRefreshToken.getId())
                .orElseThrow(MemberNotFoundException::new);

        return jwtUtil.generateAccessToken(findRefreshToken.getId(), member.getRole());
    }

    public void saveRefreshToken(String refreshToken, Long memberId) {
        refreshTokenRepository.save(new RefreshToken(refreshToken, memberId));
    }

    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }
}
