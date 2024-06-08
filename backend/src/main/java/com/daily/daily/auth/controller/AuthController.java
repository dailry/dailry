package com.daily.daily.auth.controller;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.auth.service.TokenService;
import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.common.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SuccessResponseDTO> login(@RequestBody LoginDTO loginDto) {
        TokenDTO tokenDto = authService.login(loginDto);

        ResponseCookie accessTokenCookie = cookieService.createCookie(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieService.createCookie(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());

        return ResponseEntity.ok()
                .header(SET_COOKIE, accessTokenCookie.toString())
                .header(SET_COOKIE, refreshTokenCookie.toString())
                .body(new SuccessResponseDTO());
    }

    @PostMapping("/logout")
    public SuccessResponseDTO logout(@CookieValue("AccessToken") String accessToken,
                                     @CookieValue("RefreshToken") String refreshToken, HttpServletResponse response) {
        authService.logout(response, TokenDTO.of(accessToken, refreshToken));
        return new SuccessResponseDTO();
    }

    @PostMapping("/token")
    public SuccessResponseDTO getToken(@CookieValue("AccessToken") String accessToken,
                                       @CookieValue("RefreshToken") String refreshToken, HttpServletResponse response) {
        tokenService.renewToken(response, accessToken, refreshToken);
        return new SuccessResponseDTO();
    }
}
