package com.daily.daily.auth.controller;

import com.daily.daily.auth.dto.CookieDTO;
import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.auth.service.TokenService;
import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.common.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    private final CookieService cookieService;

    @PostMapping("/login")
    public SuccessResponseDTO login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        TokenDTO tokenDto = authService.login(loginDto);

        CookieDTO cookieDTO = cookieService.setTokensInCookie(tokenDto.getAccessToken(), tokenDto.getRefreshToken());

        response.addHeader(SET_COOKIE, cookieDTO.getAccessCookie().toString());
        response.addHeader(SET_COOKIE, cookieDTO.getRefreshCookie().toString());

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
