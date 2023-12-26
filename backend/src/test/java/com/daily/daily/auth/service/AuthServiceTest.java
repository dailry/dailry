package com.daily.daily.auth.service;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.member.controller.MemberController;
import com.daily.daily.member.dto.JoinDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    MemberController memberController;

    @BeforeEach
    void join() {
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("testtset");
        joinDTO.setPassword("test1234");
        joinDTO.setNickname("난폭한사자");
        memberController.join(joinDTO);
        System.out.println("join");
    }

    @Test
    @DisplayName("loginDTO의 정보를 받아 회원가입된 정보와 일치할 경우 로그인을 진행한다.")
    void login() {

        //given
        String username = "testtset";
        String password = "test1234";

        //when
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);

        //then
        try {
            authService.login(loginDto);
            System.out.println("로그인 성공!");
        } catch (BadCredentialsException e) {
            System.out.println("일치하는 password가 존재하지 않습니다.");
        } catch (InternalAuthenticationServiceException e) {
            System.out.println("일치하는 username이 존재하지 않습니다.");
        }
    }
}
