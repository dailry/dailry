package com.daily.daily.integration.auth.jwt;

import com.daily.daily.auth.dto.JwtClaimDTO;
import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.jwt.JwtUtil;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
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

    @Test
    @DisplayName("Http 응답으로 전송되는 토큰이, jwtUtil 에서 만들어진 토큰과 같은 것인지 확인한다.")
    void checkTokenHeader() {
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        String accessToken = jwtUtil.generateAccessToken(3L, MemberRole.ROLE_MEMBER);
        String refreshToken = jwtUtil.generateRefreshToken(3L);

        jwtUtil.setTokensInCookie(mockResponse, accessToken, refreshToken);

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

        String requestBody = objectMapper.writeValueAsString(loginDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/login").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(cookie().exists("AccessToken"))
                .andExpect(cookie().exists("RefreshToken"));
    }
}
