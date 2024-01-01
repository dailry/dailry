package com.daily.daily.auth.controller;

import com.daily.daily.auth.jwt.RefreshToken;
import com.daily.daily.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public String getToken(@RequestBody RefreshToken refreshToken) {
        return tokenService.createAccessToken(refreshToken);
    }
}
