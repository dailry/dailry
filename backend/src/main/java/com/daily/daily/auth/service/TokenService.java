package com.daily.daily.auth.service;

import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.jwt.RefreshTokenRepository;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
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

    public String createAccessToken(final RefreshToken refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findById(refreshToken.getRefreshToken())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Member member = memberRepository.findById(refreshToken1.getId())
                .orElseThrow(MemberNotFoundException::new);

        return jwtUtil.generateAccessToken(refreshToken1.getId(), member.getRole());
    }
}
