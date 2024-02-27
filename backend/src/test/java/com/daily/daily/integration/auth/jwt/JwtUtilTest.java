package com.daily.daily.integration.auth.jwt;

import com.daily.daily.auth.dto.CookieDTO;
import com.daily.daily.auth.dto.JwtClaimDTO;
import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.common.service.CookieService;
import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.controller.MemberController;
import com.daily.daily.member.dto.JoinDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberController memberController;

    @Autowired
    CookieService cookieService;

    @Autowired
    AuthService authService;

    @Test
    @DisplayName("Http 응답으로 전송되는 토큰이, jwtUtil 에서 만들어진 토큰과 같은 것인지 확인한다.")
    void checkToken() {
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        System.out.println("mockResponse:" + mockResponse);

        String accessToken = jwtUtil.generateAccessToken(3L, MemberRole.ROLE_MEMBER);
        String refreshToken = jwtUtil.generateRefreshToken(3L);

        ResponseCookie accessTokenCookie = cookieService.createCookie(JwtUtil.ACCESS_TOKEN, accessToken);
        ResponseCookie refreshTokenCookie = cookieService.createCookie(JwtUtil.REFRESH_TOKEN, refreshToken);

        mockResponse.addHeader(SET_COOKIE, accessTokenCookie.toString());
        mockResponse.addHeader(SET_COOKIE, refreshTokenCookie.toString());

        Cookie test = mockResponse.getCookie("AccessToken");
        System.out.println("test:" + test);

        String responseAccessToken = mockResponse.getCookie("AccessToken").getValue();
        String responseRefreshToken = mockResponse.getCookie("RefreshToken").getValue();

        assertThat(accessToken).isEqualTo(responseAccessToken);
        assertThat(refreshToken).isEqualTo(responseRefreshToken);
    }

    @Test
    @DisplayName("생성된 토큰으로부터 Id값과 role값을 가져올 수 있는지 확인합니다.")
    void getUsernameFromJwt() {
        //given
        Long memberId = 15L;
        MemberRole role = MemberRole.ROLE_MEMBER;

        //when
        String accessToken = jwtUtil.generateAccessToken(memberId, role);

        JwtClaimDTO jwtClaimDTO = jwtUtil.extractClaims(accessToken);
        Long jwtMemberId = jwtClaimDTO.getMemberId();
        MemberRole jwtRole = jwtClaimDTO.getRole();

        //then
        System.out.println(jwtMemberId);
        System.out.println(memberId);
        assertThat(memberId).isEqualTo(jwtMemberId);
        assertThat(role).isEqualTo(jwtRole);
    }

    @Test
    @DisplayName("회원가입 이후 로그인 요청을 했을 때, 성공적으로 로그인 및 토큰을 받아왔는지 확인합니다.")
    void successfulAuthentication_test() throws Exception {
        //given
        memberController.join(new JoinDTO("testtest","12341234",null));

        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername("testtest");
        loginDto.setPassword("12341234");

        TokenDTO tokenDto = authService.login(loginDto);

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        String requestBody = objectMapper.writeValueAsString(loginDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .cookie(new Cookie("AccessToken", accessToken))
                .cookie(new Cookie("RefreshToken", refreshToken))
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk());
    }
}
