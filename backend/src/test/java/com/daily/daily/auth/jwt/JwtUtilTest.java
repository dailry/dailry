package com.daily.daily.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.daily.daily.auth.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.secret-key}")
    private String secret;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("jwt 토큰 생성이 완료됐는지 확인합니다.")
    void createJwt() throws Exception {

        //given
        String username = "testtest";
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);


        //when
        String jwtToken = jwtUtil.generateToken(loginDto.getUsername());
        System.out.println("jwtToken: " + jwtToken);

        //then
        assertThat(jwtToken.startsWith(JwtUtil.BEARER_PREFIX));
    }

    @Test
    @DisplayName("생성된 토큰을 검증합니다.")
    void verifyJwt() throws Exception {

        //given
        String jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJodHRwczovL2RhaWx5LmNvbSIsInVzZXJuYW1lIjoidGVzdHRlc3QiLCJleHAiOjE3MDMzNjU2MTd9.3hJKelQmVWoLS0OoEtRayCazqsr9cV6fM7Awk7KlbDybuhPG4N5ejeUacRhcyXj-NqRTAz5E1iTo8SIFx_h41Q";

        //when
        LoginDto loginDto = verify(jwtToken);
        System.out.println("LoginDto: " + loginDto);

        //then
        assertThat(loginDto.getUsername().equals("testtest"));
    }

    private LoginDto verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret)).build().verify(token);

        String username = decodedJWT.getClaim("username").asString();
        System.out.println("username " +  username);

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);

        return loginDto;
    }


    @Test
    @DisplayName("토큰의 헤더가 제대로 나오는지 확인합니다.")
    void checkTokenHeader() throws Exception {
        String username = "testtest";
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        String accessToken = jwtUtil.generateToken(loginDto.getUsername());
        String refreshToken = jwtUtil.createRefreshToken();

        jwtUtil.setAccessTokenHeader(mockHttpServletResponse, accessToken);
        jwtUtil.sendAccessAndRefreshToken(mockHttpServletResponse,accessToken,refreshToken);
        String headerAccessToken = mockHttpServletResponse.getHeader("Authorization");

        assertThat(headerAccessToken).isEqualTo(accessToken);
    }

    @Test
    @DisplayName("토큰으로부터 username을 가져올 수 있는지 확인합니다.")
    void getUsernameFromJwt() throws Exception{
        String username = "testtest";
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);

        String accessToken = jwtUtil.generateToken(loginDto.getUsername());
        accessToken = accessToken.substring(JwtUtil.BEARER_PREFIX.length());

        assertThat(jwtUtil.extractUsername(accessToken).equals(username));
    }

    @Test
    @DisplayName("성공적으로 로그인 및 토큰을 받아왔는지 확인합니다.")
    public void successfulAuthentication_test() throws Exception {

        LoginDto loginDto = new LoginDto();
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
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith(JwtUtil.BEARER_PREFIX));
    }

}
