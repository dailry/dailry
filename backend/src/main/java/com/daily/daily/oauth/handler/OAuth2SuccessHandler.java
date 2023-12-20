package com.daily.daily.oauth.handler;


import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.oauth.OAuth2CustomUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");

        try {
            OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();

            if(oAuth2User.getMemberRole() == MemberRole.MEMBER) {
                String accessToken = jwtUtil.generateToken(oAuth2User.getUsername());
                response.addHeader(jwtUtil.getAccessHeader(), accessToken);
                response.sendRedirect("/"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트

                jwtUtil.sendAccessAndRefreshToken(response, accessToken, null);
            } else {
                loginSuccess(response, oAuth2User);
            }
        } catch (Exception e) {
            throw e;
        }
    }
    private void loginSuccess(HttpServletResponse response, OAuth2CustomUser oAuth2User) throws IOException {
        String accessToken = jwtUtil.generateToken(oAuth2User.getUsername());
        String refreshToken = jwtUtil.createRefreshToken();
        response.addHeader(jwtUtil.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtUtil.getRefreshHeader(), "Bearer " + refreshToken);

        jwtUtil.sendAccessAndRefreshToken(response, accessToken, refreshToken);
    }

}
