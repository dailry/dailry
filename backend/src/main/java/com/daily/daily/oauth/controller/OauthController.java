package com.daily.daily.oauth.controller;

import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {
    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
