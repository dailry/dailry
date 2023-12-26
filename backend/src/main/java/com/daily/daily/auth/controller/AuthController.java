package com.daily.daily.auth.controller;

import com.daily.daily.auth.dto.JwtClaimDTO;
import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.service.AuthService;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ExceptionResponseDTO> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        JwtClaimDTO claimDTO = authService.login(loginDto);

        response.setHeader("Authorization", jwtUtil.generateToken(claimDTO.getMemberId(), claimDTO.getRole()));
        return ResponseEntity.ok().body(new ExceptionResponseDTO("로그인 성공", HttpStatus.OK.value()));
    }
}
