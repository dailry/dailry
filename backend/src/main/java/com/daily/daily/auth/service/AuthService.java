package com.daily.daily.auth.service;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.exception.LoginFailureException;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.common.service.CookieService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.daily.daily.auth.jwt.JwtUtil.ACCESS_TOKEN;
import static com.daily.daily.auth.jwt.JwtUtil.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final CookieService cookieService;
    private final TokenService tokenService;
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

        tokenService.saveRefreshToken(refreshToken, findMember.getId());
        return new TokenDTO(accessToken, refreshToken);
    }

    public void logout(HttpServletResponse response, TokenDTO tokenDto) {
        cookieService.deleteCookie(response, ACCESS_TOKEN);
        cookieService.deleteCookie(response, REFRESH_TOKEN);
        tokenService.deleteRefreshToken(tokenDto.getRefreshToken());
    }
}
