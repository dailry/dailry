package com.daily.daily.dailry.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.dto.DailryFindDTO;
import com.daily.daily.dailry.service.DailryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.daily.daily.dailry.fixture.DailryFixture.*;
import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DailryController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class DailryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DailryService dailryService;

    @Test
    @DisplayName("정상적으로 다일리가 생성됐는지 확인합니다.")
    @WithMockUser
    void createDialry() throws Exception {
        //given, when
        DailryDTO response = 다일리_응답_DTO();
        given(dailryService.create(any(), any())).willReturn(response);

        ResultActions resultActions =  mockMvc.perform(post("/api/dailry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(다일리_생성_DTO()))
                        .with(csrf().asHeader())
        );


        resultActions.andDo(document("다일리 생성",
                requestFields(
                        fieldWithPath("title").type(STRING).description("다일리 제목")
                ),
                responseFields(
                        fieldWithPath("dailryId").type(NUMBER).description("다일리 id"),
                        fieldWithPath("title").type(STRING).description("다일리 제목")
        )));
    }

    @Test
    @DisplayName("정상적으로 다일리가 수정됐는지 확인합니다.")
    @WithMockUser
    void updateDailry() throws Exception {

        //given, when
        DailryDTO response = 수정된_다일리_응답_DTO();
        given(dailryService.update(any(), any(), any())).willReturn(response);

        ResultActions resultActions = mockMvc.perform(patch("/api/dailry/{dailryId}", DAILRY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(다일리_수정_DTO()))
                .with(csrf().asHeader())
        );

        resultActions.andDo(document("다일리 수정",
                pathParameters(
                        parameterWithName("dailryId").description("다일리 id")
                ),
                requestFields(
                        fieldWithPath("title").type(STRING).description("다일리 제목")
                ),
                responseFields(
                        fieldWithPath("dailryId").type(NUMBER).description("다일리 id"),
                        fieldWithPath("title").type(STRING).description("다일리 제목")
                )));
    }

    @Test
    @DisplayName("정상적으로 다일리가 조회됐는지 확인합니다.")
    @WithMockUser
    void findDailry() throws Exception {

        DailryDTO 응답결과 = 다일리_조회_DTO();
        given(dailryService.find(any(), any())).willReturn(응답결과);


        ResultActions resultActions = mockMvc.perform(get("/api/dailry/{dailryId}", DAILRY_ID)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(document("다일리 조회",
                pathParameters(
                        parameterWithName("dailryId").description("다일리 id")
                ),
                responseFields(
                        fieldWithPath("dailryId").type(NUMBER).description("다일리 id"),
                        fieldWithPath("title").type(STRING).description("다일리 제목")
                )));
    }

    @Test
    @DisplayName("정상적으로 전체 다일리가 조회됐는지 확인합니다.")
    @WithMockUser
    void findAllDailry() throws Exception {

        List<DailryFindDTO> 응답결과 = 다일리_리스트_응답_DTO();
        given(dailryService.findAll(any())).willReturn(응답결과);

        ResultActions resultActions = mockMvc.perform(get("/api/dailry")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf().asHeader())
        );

        resultActions.andDo(document("전체 다일리 조회",
                responseFields(
                        fieldWithPath("[].dailryId").type(NUMBER).description("다일리 id"),
                        fieldWithPath("[].title").type(STRING).description("다일리 제목")
                )));
    }

    @Test
    @DisplayName("정상적으로 다일리가 삭제됐는지 확인합니다.")
    @WithMockUser
    void deleteDailry() throws Exception {
        //given, when
        ResultActions resultActions = mockMvc.perform(delete("/api/dailry/{dailryId}", DAILRY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf().asHeader())
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.statusCode").value(200));

        resultActions.andDo(document("다일리 삭제",
                pathParameters(
                        parameterWithName("dailryId").description("다일리 id")
                )
        ));
    }


}
