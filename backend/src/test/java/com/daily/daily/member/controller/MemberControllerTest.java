package com.daily.daily.member.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.member.dto.EmailDTO;
import com.daily.daily.member.dto.EmailVerifyDTO;
import com.daily.daily.member.dto.JoinDTO;
import com.daily.daily.member.dto.MemberInfoDTO;
import com.daily.daily.member.dto.NicknameDTO;
import com.daily.daily.member.dto.PasswordRecoverDTO;
import com.daily.daily.member.dto.PasswordTokenDTO;
import com.daily.daily.member.dto.PasswordUpdateDTO;
import com.daily.daily.member.exception.DuplicatedUsernameException;
import com.daily.daily.member.service.MemberEmailService;
import com.daily.daily.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MemberController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@MockBean(JpaMetamodelMappingContext.class)

class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    MemberEmailService memberEmailService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("회원가입이 성공한 경우의 응답을 검사한다.")
    void joinSuccessCase() throws Exception {
        //given
        JoinDTO joinDTO = new JoinDTO("geonwoo123", "pass1234", null);

        MemberInfoDTO expected = new MemberInfoDTO();
        expected.setId(1L);
        expected.setUsername("geonwoo123");
        expected.setNickname("난폭한사자");

        given(memberService.join(Mockito.any())).willReturn(expected);
        //when
        ResultActions joinActions = mockMvc.perform(post("/api/member/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinDTO))
                .with(csrf())
        );
        //then
        String content = joinActions.andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        MemberInfoDTO actual = objectMapper.readValue(content, MemberInfoDTO.class);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @WithMockUser
    @DisplayName("중복된 username으로 회원가입이 실패하였을 경우 응답을 검사한다.")
    void joinFailureCase() throws Exception {
        //given
        JoinDTO joinDTO = new JoinDTO("geonwoo123", "pass1234", null);

        given(memberService.join(Mockito.any())).willThrow(DuplicatedUsernameException.class);

        //when
        ResultActions joinActions = mockMvc.perform(post("/api/member/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinDTO))
                .with(csrf())

        );

        //then
        joinActions.andExpect(status().isConflict())
                .andExpect(jsonPath("$.statusCode").value(409));
    }

    @Test
    @WithMockUser
    void getMemberByAccessToken() throws Exception {
        //given
        MemberInfoDTO expected = new MemberInfoDTO();
        expected.setId(1L);
        expected.setUsername("geonwoo123");
        expected.setNickname("난폭한사자");

        given(memberService.findById(Mockito.any())).willReturn(expected);
        String token = "testJwtToken";

        //when
        ResultActions actions = mockMvc.perform(get("/api/member")
                .header("Authorization", token)
                .with(csrf())
        );

        //then
        String content = actions.andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        MemberInfoDTO actual = objectMapper.readValue(content, MemberInfoDTO.class);
        assertThat(actual).isEqualTo(expected);
    }

    @WithMockUser
    @Test
    @DisplayName("중복된 username이 있을경우 응답을 검사한다.")
    void checkDuplicatedUsernameCase() throws Exception {
        //given
        String testUsername = "username123";
        given(memberService.existsByUsername(testUsername)).willReturn(true);

        //when
        ResultActions perform = mockMvc.perform(get("/api/member/check-username")
                .param("username", testUsername)
        );

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.duplicated").value(true));
    }

    @WithMockUser
    @Test
    @DisplayName("중복된 username이 없는경우 응답을 검사한다.")
    void checkNoneDuplicatedUsernameCase() throws Exception {
        //given
        String testUsername = "username123";
        given(memberService.existsByUsername(testUsername)).willReturn(false);

        //when
        ResultActions perform = mockMvc.perform(get("/api/member/check-username")
                .param("username", testUsername)
        );

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.duplicated").value(false));

    }

    @WithMockUser
    @Test
    @DisplayName("중복된 nickname이 있는경우 반환값을 검사한다.")
    void checkDuplicatedNicknameCase() throws Exception {
        //given
        String testNickname = "nickname";
        given(memberService.existsByNickname(testNickname)).willReturn(true);

        //when
        ResultActions perform = mockMvc.perform(get("/api/member/check-nickname")
                .param("nickname", testNickname)
        );

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.duplicated").value(true));
    }

    @WithMockUser
    @Test
    @DisplayName("중복된 nickname이 없는경우 반환값을 검사한다.")
    void checkNoneDuplicatedNicknameCase() throws Exception {
        //given
        String testNickname = "nickname";
        given(memberService.existsByNickname(testNickname)).willReturn(false);

        //when
        ResultActions perform = mockMvc.perform(get("/api/member/check-nickname")
                .param("nickname", testNickname)
        );

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.duplicated").value(false));
    }

    @WithMockUser
    @Test
    void updateNickname() throws Exception {
        //given
        NicknameDTO nicknameDTO = new NicknameDTO("mynickname");

        MemberInfoDTO expected = new MemberInfoDTO();
        expected.setId(1L);
        expected.setUsername("geonwoo123");
        expected.setNickname("난폭한사자");

        given(memberService.updateNickname(Mockito.any(), Mockito.any())).willReturn(expected);
        //when
        ResultActions perform = mockMvc.perform(patch("/api/member/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nicknameDTO))
                .with(csrf())
        );
        //then
        String content = perform.andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        MemberInfoDTO actual = objectMapper.readValue(content, MemberInfoDTO.class);
        assertThat(actual).isEqualTo(expected);
    }

    @WithMockUser
    @Test
    void updatePassword() throws Exception {
        //given
        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO("12345678", "23456789");

        //when
        ResultActions perform = mockMvc.perform(patch("/api/member/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passwordUpdateDTO))
                .with(csrf())
        );
        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @WithMockUser
    @Test
    @DisplayName("이메일 인증번호를 성공적으로 전송했을 때 응답값을 테스트한다.")
    void sendCertificationNumber() throws Exception {
        //given
        EmailDTO emailDTO = new EmailDTO("qkrrjsdn123@naver.com");

        //when
        ResultActions perform = mockMvc.perform(post("/api/member/email-verification/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailDTO))
                .with(csrf())
        );
        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @WithMockUser
    @Test
    @DisplayName("사용자가 입력한 이메일 인증번호가 일치했을 때 응답값을 테스트한다.")
    void verifyEmailAndRegister() throws Exception {
        //given
        EmailVerifyDTO emailVerifyDTO = new EmailVerifyDTO("qkrrjsdn123@naver.com", "315162");

        //when
        ResultActions perform = mockMvc.perform(post("/api/member/email-verification/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailVerifyDTO))
                .with(csrf())
        );
        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @WithMockUser
    @Test
    @DisplayName("사용자가 이메일과 함꼐 아이디 찾기를 요청했을 때, 올바른 이메일일 경우 응답값을 테스트한다.")
    void recoverUsername() throws Exception {
        //given
        EmailDTO emailDTO = new EmailDTO("qkrrjsdn123@naver.com");

        //when
        ResultActions perform = mockMvc.perform(post("/api/member/recover-username")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailDTO))
                .with(csrf())
        );

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @WithMockUser
    @Test
    @DisplayName("사용자가 비밀번호 초기화 요청을 했을 때, username과 이메일이 올바를 경우 응답값을 테스트 한다.")
    void recoverPassword() throws Exception {
        //given
        PasswordRecoverDTO passwordRecoverDTO = new PasswordRecoverDTO();
        passwordRecoverDTO.setEmail("qkrrjsdn123@naver.com");
        passwordRecoverDTO.setUsername("rjsdn123");

        //when
        ResultActions perform = mockMvc.perform(post("/api/member/recover-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passwordRecoverDTO))
                .with(csrf())
        );

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @WithMockUser
    @Test
    @DisplayName("사용자가 유효한 비밀번호 변경 토큰과 함께 비밀번호 변경 요청을 했을 때 응답값을 테스트 한다.")
    void updatePasswordByResetToken() throws Exception {
        //given
        PasswordTokenDTO passwordTokenDTO = new PasswordTokenDTO();
        passwordTokenDTO.setPasswordResetToken("유효한 토큰");
        passwordTokenDTO.setPassword("12345678");

        //when
        ResultActions perform = mockMvc.perform(patch("/api/member/recover-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passwordTokenDTO))
                .with(csrf())
        );

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.statusCode").value(200));
    }
}
