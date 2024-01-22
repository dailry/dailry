package com.daily.daily.dailry.controller;

import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.dto.TokenDTO;
import com.daily.daily.auth.service.AuthService;
import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.dto.DailryUpdateDTO;
import com.daily.daily.dailry.service.DailryService;
import com.daily.daily.member.controller.MemberController;
import com.daily.daily.member.dto.JoinDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureRestDocs
public class DailryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberController memberController;

    @Autowired
    DailryService dailryService;

    @Autowired
    AuthService authService;

    @BeforeEach
    void join() throws Exception {
        JoinDTO joinDTO = new JoinDTO("testtest", "12341234", "잠자는사자");
        memberController.join(joinDTO);
        String username = "testtest";
        String password = "12341234";
        LoginDTO loginDto = new LoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);
        TokenDTO tokenDto = authService.login(loginDto);

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        mockMvc.perform(post("/api/login")
                .cookie(new Cookie("AccessToken", accessToken))
                .cookie(new Cookie("RefreshToken", refreshToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));
    }

    @Test
    @DisplayName("정상적으로 다일리가 생성됐는지 확인합니다.")
    @WithMockUser
    void createDialry() throws Exception {

        DailryUpdateDTO dailryUpdateDTO = new DailryUpdateDTO();
        dailryUpdateDTO.setTitle("오늘의 다일리");
        DailryDTO dailryDTO = dailryService.create(4L, dailryUpdateDTO);

        ResultActions resultActions =  mockMvc.perform(post("/api/dailry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dailryDTO)));


        resultActions.andDo(document("다일리 생성",
                requestFields(
                        fieldWithPath("id").type(NUMBER).description("멤버 id"),
                        fieldWithPath("title").type(STRING).description("다일리 제목")
                ),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("다일리 id"),
                        fieldWithPath("title").type(STRING).description("다일리 제목")
        )));
    }

    @Test
    @DisplayName("정상적으로 다일리가 수정됐는지 확인합니다.")
    @WithMockUser
    void updateDailry() throws Exception {

        Long dailryId = 1L;

        DailryUpdateDTO dailryUpdateDTO = new DailryUpdateDTO();
        dailryUpdateDTO.setTitle("오늘의 다일리");
        dailryService.create(1L, dailryUpdateDTO);

        DailryUpdateDTO updatedDailryUpdateDTO = new DailryUpdateDTO();
        updatedDailryUpdateDTO.setTitle("내일의 다일리");
        DailryDTO dailryDTO = dailryService.update(dailryId, updatedDailryUpdateDTO);

        ResultActions resultActions = mockMvc.perform(patch("/api/dailry/{dailryId}", dailryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dailryDTO)));

        resultActions.andDo(document("다일리 수정",
                requestFields(
                        fieldWithPath("id").type(NUMBER).description("다일리 id"),
                        fieldWithPath("title").type(STRING).description("다일리 제목")
                ),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("다일리 id"),
                        fieldWithPath("title").type(STRING).description("다일리 제목")
                )));
    }

    @Test
    @DisplayName("정상적으로 다일리가 조회됐는지 확인합니다.")
    @WithMockUser
    void findDailry() throws Exception {

        DailryUpdateDTO dailryUpdateDTO = new DailryUpdateDTO();
        dailryUpdateDTO.setTitle("오늘의 다일리");
        DailryDTO dailryDTO = dailryService.create(3L, dailryUpdateDTO);
        Long dailryId = dailryDTO.getId();

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/dailry/{dailryId}", dailryId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(document("다일리 조회",
                pathParameters(
                        parameterWithName("dailryId").description("다일리 id")
                ),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("다일리 id"),
                        fieldWithPath("title").type(STRING).description("다일리 제목")
                )));
    }

    @Test
    @DisplayName("정상적으로 다일리가 삭제됐는지 확인합니다.")
    @WithMockUser
    void deleteDailry() throws Exception {
        Long dailryId = 1L;
        DailryUpdateDTO dailryUpdateDTO = new DailryUpdateDTO();
        dailryUpdateDTO.setTitle("오늘의 다일리");
        dailryService.create(2L, dailryUpdateDTO);;

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/dailry/{dailryId}", dailryId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(document("다일리 삭제",
                pathParameters(
                        parameterWithName("dailryId").description("다일리 id")
                ),
                responseFields(
                        fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                        fieldWithPath("successful").type(BOOLEAN).description("성공여부")
                )));
    }


}
