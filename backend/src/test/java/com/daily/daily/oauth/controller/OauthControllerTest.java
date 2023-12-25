package com.daily.daily.oauth.controller;

import com.daily.daily.auth.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OauthControllerTest {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("OAuth 로그인을 진행한다.")
    void loginOAuth() throws Exception {

        DefaultOAuth2User user = makeDefaultOAuth2User();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());

        ResultActions resultActions = mockMvc.perform(get("/oauth/loginInfo").with(authentication(authentication)));

        resultActions.andExpect(status().isOk());
    }

    private DefaultOAuth2User makeDefaultOAuth2User() {

        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put("sub", "107986715009708108628");
        customAttribute.put("provider", "google");
        customAttribute.put("name", "김수현");
        customAttribute.put("email", "suhyun@naver.com");

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                "sub");
    }
}
