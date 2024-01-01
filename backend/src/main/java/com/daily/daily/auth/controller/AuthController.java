package com.daily.daily.auth.controller;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.auth.service.TokenService;
import com.daily.daily.common.dto.ExceptionResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ExceptionResponseDTO> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        TokenDTO tokenDto = authService.login(loginDto);
        response.setHeader(jwtUtil.getAccessHeader(), tokenDto.getAccessToken());
        response.setHeader(jwtUtil.getRefreshHeader(), tokenDto.getRefreshToken());
        return ResponseEntity.ok().body(new ExceptionResponseDTO("로그인 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/token")
    public String getToken(@RequestBody RefreshToken refreshToken) {
        return tokenService.createAccessToken(refreshToken);
    }
}
