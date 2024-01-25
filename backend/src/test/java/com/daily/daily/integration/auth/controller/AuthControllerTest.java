package com.daily.daily.integration.auth.controller;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.exception.LoginFailureException;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.member.controller.MemberController;
import com.daily.daily.member.dto.JoinDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureRestDocs
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberController memberController;

    @BeforeEach
    void join() {
        JoinDTO joinDTO1 = new JoinDTO("geonwoo1234", "pass1234", "잠자는하마");
        JoinDTO joinDTO2 = new JoinDTO("testtest", "12341234", "잠자는사자");
        memberController.join(joinDTO1);
        memberController.join(joinDTO2);
    }

    @Test
    @DisplayName("일치하는 username과 password가 있다면 로그인에 성공한다.")
    void loinSuccess() {

        String username = "testtest";
        String password = "12341234";
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);

        try {
            authService.login(loginDto);
            System.out.println("test");
        } catch (IllegalArgumentException e) {
            System.out.println("로그인 실패");
        }
    }

    @Test
    @DisplayName("일치하는 username과 password가 없다면 로그인에 실패한다.")
    void loinFailure() {
        String username = "testtest12";
        String password = "12341234";
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);

        try {
            authService.login(loginDto);
        } catch (LoginFailureException e) {
            System.out.println("로그인 정보가 올바르지 않습니다.");
        }
    }

    @Test
    @WithMockUser
    @DisplayName("정상적으로 응답 후 토큰값이 리턴되었는지 확인한다.")
    void loginReturn() throws Exception  {
        String username = "testtest";
        String password = "12341234";
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);
        TokenDTO tokenDto = authService.login(loginDto);

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        ResultActions resultActions =  mockMvc.perform(post("/api/login")
                        .cookie(new Cookie("AccessToken", accessToken))
                        .cookie(new Cookie("RefreshToken", refreshToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.statusCode").value(200));

        resultActions.andDo(document("로그인",
                requestFields(
                        fieldWithPath("username").type(STRING).description("아이디"),
                        fieldWithPath("password").type(STRING).description("비밀번호")
                ),
                responseFields(
                        fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                        fieldWithPath("successful").type(BOOLEAN).description("성공여부")
                )));
    }

    @Test
    @WithMockUser
    @DisplayName("정상적으로 로그인이 되는지 테스트합니다.")
    public void shouldAuthenticate() throws Exception {
        String username = "testtest";
        String password = "12341234";
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);
        Gson GSON = new GsonBuilder().create();
        String userJSON = GSON.toJson(loginDto);
        TokenDTO tokenDto = authService.login(loginDto);

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        MvcResult mvcResult = mockMvc.perform(post("/api/login")
                .cookie(new Cookie("AccessToken", accessToken))
                .cookie(new Cookie("RefreshToken", refreshToken))
                .content(userJSON)).andReturn();
        String bearer = mvcResult.getResponse().getContentAsString();

        assertThat(!bearer.isEmpty());
    }

    @Test
    @WithMockUser
    @DisplayName("refreshToken을 이용해서 acceessToken을 갱신한다")
    void renewAccessToken() throws Exception  {
        String username = "testtest";
        String password = "12341234";
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);
        TokenDTO tokenDto = authService.login(loginDto);

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        ResultActions resultActions =  mockMvc.perform(post("/api/token")
                .cookie(new Cookie("AccessToken", accessToken))
                .cookie(new Cookie("RefreshToken", refreshToken))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(document("accesstoken갱신",
                responseFields(
                        fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                        fieldWithPath("successful").type(BOOLEAN).description("성공여부")
                )));
    }

    @Test
    @WithMockUser
    @DisplayName("로그아웃을 실행합니다.")
    void logoutSuccess() throws Exception {
        String username = "testtest";
        String password = "12341234";
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);
        TokenDTO tokenDto = authService.login(loginDto);

        String refreshToken = tokenDto.getRefreshToken();
        String accessToken = tokenDto.getAccessToken();

        ResultActions resultActions = mockMvc.perform(post("/api/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("AccessToken", accessToken))
                .cookie(new Cookie("RefreshToken", refreshToken)));

        resultActions.andDo(document("로그아웃",
                responseFields(
                        fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                        fieldWithPath("successful").type(BOOLEAN).description("성공여부")
                )));
    }

    @Test
    @WithMockUser
    @DisplayName("정상적으로 회원탈퇴가 이루어지는지 테스트한다.")
    void withdrawalMember() throws Exception {

        LoginDTO loginDto = new LoginDTO("testtest", "12341234");

        TokenDTO tokenDto = authService.login(loginDto);
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        mockMvc.perform(post("/api/login")
                .cookie(new Cookie("AccessToken", accessToken))
                .cookie(new Cookie("RefreshToken", refreshToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));

        ResultActions resultActions =  mockMvc.perform(post("/api/members/withdrawal")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(document("회원탈퇴",
                responseFields(
                        fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                        fieldWithPath("successful").type(BOOLEAN).description("성공여부")
                )));
    }
}