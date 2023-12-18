package com.daily.daily.auth.controller;

import com.daily.daily.auth.dto.LoginDto;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.common.dto.CommonResponseDTO;
import com.daily.daily.member.service.MemberService;
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
    public ResponseEntity<CommonResponseDTO> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        try {
            authService.login(loginDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        response.setHeader("Authorization", jwtUtil.generateToken(loginDto.getUsername()));
        return ResponseEntity.ok().body(new CommonResponseDTO("로그인 성공", HttpStatus.OK.value()));
    }
}
