package com.daily.daily.auth.controller;

import com.daily.daily.auth.dto.LoginDto;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.member.controller.MemberController;
import com.daily.daily.member.dto.JoinDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("geonwoo123");
        joinDTO.setPassword("pass1234");
        joinDTO.setNickname("잠자는하마");
        memberController.join(joinDTO);
        System.out.println("join");
    }

    @Test
    @DisplayName("일치하는 username과 password가 있다면 로그인에 성공한다.")
    void loinSuccess() throws Exception  {

        String username = "geonwoo123";
        String password = "pass1234";
        LoginDto loginDto = new LoginDto();
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
        String username = "geonwoo123";
        String password = "pass12345";
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);
        loginDto.setPassword(password);

        try {
            authService.login(loginDto);
        } catch (BadCredentialsException e) {
            System.out.println("일치하는 password가 존재하지 않습니다.");
        } catch (InternalAuthenticationServiceException e) {
            System.out.println("일치하는 username이 존재하지 않습니다.");
        }
    }

    @Test
    @DisplayName("정상적으로 응답 후 토큰값이 리턴되었는지 확인한다.")
    void loginReturn() throws Exception  {

        String url = "http://localhost:8080/api/login";

        String username = "testtest";
        String password = "12341234";
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);
        loginDto.setPassword(password);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(header().exists("Authorization"));
    }

    @Test
    @DisplayName("정상적으로 로그인이 되는지 테스트합니다.")
    public void shouldAuthenticate() throws Exception {
        String username = "testtest";
        String password = "12341234";
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);
        loginDto.setPassword(password);
        Gson GSON = new GsonBuilder().create();

        String userJSON = GSON.toJson(loginDto);
        MvcResult mvcResult = mockMvc.perform(post("/api/login").content(userJSON)).andReturn();
        String bearer = mvcResult.getResponse().getContentAsString();

        assertThat(!bearer.isEmpty());
    }

    @Test
    @DisplayName("로그인을 테스트합니다.(1)")
    public void loginTest1() throws Exception {
        // given
        Map<String, String> map = new HashMap<>();
        map.put("username", "testtest");
        map.put("password", "12341234");
        String content = new ObjectMapper().writeValueAsString(map);

        // when
        MockHttpServletRequestBuilder reqBuilder
                = post("/api/login").contentType("application/json").content(content);
        ResultActions action = mockMvc.perform(reqBuilder);

        // then
        action.andExpect(status().is(200));
    }

    @DisplayName("로그인을 테스트합니다.(2)")
    @Test
    void loginTest2() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "testtest");
        jsonObject.put("password", "12341234");

        ResultActions result = mockMvc.perform(post("/api/login")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = result.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
