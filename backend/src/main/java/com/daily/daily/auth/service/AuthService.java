package com.daily.daily.auth.service;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.exception.LoginFailureException;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.jwt.RefreshTokenRepository;
import com.daily.daily.common.service.CookieService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CookieService cookieService;
    public TokenDTO login(LoginDTO loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        Member findMember = memberRepository.findByUsername(username)
                .orElseThrow(LoginFailureException::new);

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new LoginFailureException();
        }

        String accessToken = jwtUtil.generateAccessToken(findMember.getId(), findMember.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(findMember.getId());

        RefreshToken redis = new RefreshToken(refreshToken, findMember.getId());
        refreshTokenRepository.save(redis);

        return new TokenDTO(
                accessToken,
                refreshToken
        );
    }

    public void logout(HttpServletResponse response, TokenDTO tokenDto) {
        cookieService.deleteCookie(response, "AccessToken");
        cookieService.deleteCookie(response, "RefreshToken");
        refreshTokenRepository.deleteById(tokenDto.getRefreshToken());
    }
}
