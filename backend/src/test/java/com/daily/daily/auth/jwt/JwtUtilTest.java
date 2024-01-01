package com.daily.daily.auth.jwt;

import com.daily.daily.auth.dto.JwtClaimDTO;
import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.controller.MemberController;
import com.daily.daily.member.dto.JoinDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @DisplayName("jwt 토큰 생성이 완료됐는지 확인합니다.")
    void createJwt() {

        //given
        Long id = 15L;
        MemberRole role = MemberRole.ROLE_MEMBER;
        //when
        String jwtToken = jwtUtil.generateAccessToken(id, role);
        System.out.println("jwtToken: " + jwtToken);

        //then
        assertThat(jwtToken.startsWith(JwtUtil.BEARER_PREFIX));
    }

    @Test
    @DisplayName("토큰의 헤더가 제대로 나오는지 확인합니다.")
    void checkTokenHeader() {
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        String accessToken = jwtUtil.generateAccessToken(3L, MemberRole.ROLE_MEMBER);
        String refreshToken = jwtUtil.createRefreshToken();

        jwtUtil.setAccessTokenHeader(mockHttpServletResponse, accessToken);
        jwtUtil.sendAccessAndRefreshToken(mockHttpServletResponse,accessToken,refreshToken);
        String headerAccessToken = mockHttpServletResponse.getHeader("Authorization");

        assertThat(headerAccessToken).isEqualTo(accessToken);
    }

    @Test
    @DisplayName("생성된 토큰으로부터 Id값과 role값을 가져올 수 있는지 확인합니다.")
    void getUsernameFromJwt() {
        //given
        Long memberId = 15L;
        MemberRole role = MemberRole.ROLE_MEMBER;

        //when
        String accessToken = jwtUtil.generateAccessToken(memberId, role);
        accessToken = accessToken.substring(JwtUtil.BEARER_PREFIX.length());

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
    @DisplayName("성공적으로 로그인 및 토큰을 받아왔는지 확인합니다.")
    void successfulAuthentication_test() throws Exception {
        memberController.join(new JoinDTO("testtest","12341234",null));
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername("testtest");
        loginDto.setPassword("12341234");
        String requestBody = objectMapper.writeValueAsString(loginDto);
        System.out.println("테스트 : " + requestBody);

        ResultActions resultActions = mockMvc.perform(post("/api/login").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String jwtToken = resultActions.andReturn().getResponse().getHeader("Authorization");

        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + jwtToken);

        resultActions.andExpect(status().isOk());
        assertTrue(jwtToken.startsWith(JwtUtil.BEARER_PREFIX));
    }
}
