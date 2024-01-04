package com.daily.daily.oauth.handler;

import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.oauth.OAuth2CustomUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("OAuth2 Login 성공!");

        try {
            OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(oAuth2User.getMember().getId(),oAuth2User.getMember().getRole());
            String refreshToken = jwtUtil.generateRefreshToken(oAuth2User.getMember().getId());
            response.addHeader(jwtUtil.getAccessHeader(), accessToken);
            response.addHeader(jwtUtil.getRefreshHeader(), refreshToken);
            response.sendRedirect("http://localhost:3000");
            jwtUtil.sendAccessAndRefreshToken(response, accessToken, null);
        } catch (Exception e) {
            log.error("OAuth2 Login 성공 후 예외 발생", e);
        }
    }
}
