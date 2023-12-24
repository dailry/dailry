package com.daily.daily.member.controller;

import com.daily.daily.common.dto.ExceptionResponseDTO;
import com.daily.daily.member.dto.JoinDTO;
import com.daily.daily.member.dto.MemberInfoDTO;
import com.daily.daily.member.dto.NicknameDTO;
import com.daily.daily.member.dto.PasswordUpdateDTO;
import com.daily.daily.member.exception.DuplicatedUsernameException;
import com.daily.daily.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("회원가입이 성공한 경우의 응답을 검사한다.")
    void joinSuccessCase() throws Exception {
        //given
        JoinDTO joinDTO = new JoinDTO("geonwoo123", "pass1234", null);
        MemberInfoDTO expected = new MemberInfoDTO(1L, "geonwoo123", "난폭한사자");

        given(memberService.join(Mockito.any())).willReturn(expected);
        //when
        ResultActions joinActions = mockMvc.perform(post("/api/member/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(joinDTO))
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
                .content(objectMapper.writeValueAsBytes(joinDTO))
                .with(csrf())

        );

        //then
        joinActions.andExpect(status().isConflict())
                .andExpect(jsonPath("$.statusCode").value(409));
    }

    /**
     * 얘는 Authnetication Principal 리팩토링 하고 작성하는게 좋을듯
     */
    @Test
    @WithMockUser
    void getMemberByAccessToken() {
        String token = "testJwtToken";
        MemberInfoDTO expected = new MemberInfoDTO(1L, "geonwoo123", "미친고양이");

        
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
    void checkDuplicatedNicknameCase() throws Exception{
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
    void checkNoneDuplicatedNicknameCase() throws Exception{
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

    @Test
    void checkDuplicatedEmail() {
    }

    @WithMockUser
    @Test
    void updateNickname() throws Exception {
        //given
        NicknameDTO nicknameDTO = new NicknameDTO("mynickname");
        MemberInfoDTO expected = new MemberInfoDTO(1L, "geonwoo123", "난폭한사자");

        given(memberService.updateNickname(Mockito.any(), Mockito.any())).willReturn(expected);
        //when
        ResultActions perform = mockMvc.perform(patch("/api/member/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(nicknameDTO))
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
    void updatePassword() throws Exception{
        //given
        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO("12345678","23456789");

        //when
        ResultActions perform = mockMvc.perform(patch("/api/member/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(passwordUpdateDTO))
                .with(csrf())
        );
        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.statusCode").value(200));
    }
}
