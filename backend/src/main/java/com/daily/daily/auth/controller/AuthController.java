package com.daily.daily.auth.controller;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.auth.service.TokenService;
import com.daily.daily.common.dto.CommonResponseDTO;
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
    public ResponseEntity<CommonResponseDTO> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        TokenDTO tokenDto = authService.login(loginDto);

        jwtUtil.setTokensInCookie(response, tokenDto.getAccessToken(), tokenDto.getRefreshToken());

        return ResponseEntity.ok().body(new CommonResponseDTO(true, HttpStatus.OK.value()));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDTO> logout(@CookieValue("AccessToken") String accessToken,
                       @CookieValue("RefreshToken") String refreshToken) {

        authService.logout(TokenDTO.of(accessToken, refreshToken));
        return ResponseEntity.ok().body(new CommonResponseDTO(true, HttpStatus.OK.value()));
    }

    @PostMapping("/token")
    public TokenDTO getToken(@CookieValue("RefreshToken") RefreshToken refreshToken) {
        return new TokenDTO(tokenService.createAccessToken(refreshToken), refreshToken.getRefreshToken());
    }
}
