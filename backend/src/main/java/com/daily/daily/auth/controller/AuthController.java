package com.daily.daily.auth.controller;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.auth.service.TokenService;
import com.daily.daily.common.dto.SuccessResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public SuccessResponseDTO login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        TokenDTO tokenDto = authService.login(loginDto);

        jwtUtil.setTokensInCookie(response, tokenDto.getAccessToken(), tokenDto.getRefreshToken());

        return new SuccessResponseDTO();
    }

    @PostMapping("/logout")
    public SuccessResponseDTO logout(@CookieValue("AccessToken") String accessToken,
                                     @CookieValue("RefreshToken") String refreshToken, HttpServletResponse response) {
        authService.logout(response, TokenDTO.of(accessToken, refreshToken));
        return new SuccessResponseDTO();
    }

    @PostMapping("/token")
    public SuccessResponseDTO getToken(@CookieValue("AccessToken") String accessToken,
                                       @CookieValue("RefreshToken") RefreshToken refreshToken, HttpServletResponse response) {
        tokenService.renewToken(response, accessToken, refreshToken);
        return new SuccessResponseDTO();
    }
}
