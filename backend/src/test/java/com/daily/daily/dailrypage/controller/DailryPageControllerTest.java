package com.daily.daily.dailrypage.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.dailrypage.service.DailryPageService;
import com.daily.daily.testutil.document.RestDocsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

import static com.daily.daily.dailrypage.fixture.DailryPageFixture.*;
import static com.daily.daily.post.fixture.PostFixture.게시글_요청_DTO_JSON_파일;
import static com.daily.daily.post.fixture.PostFixture.다일리_페이지_이미지_파일;
import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DailryPageController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class DailryPageControllerTest {

    @MockBean
    DailryPageService dailryPageService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    @DisplayName("createPage() - 다일리 페이지 생성 메서드 테스트")
    class createPage {

        @Test
        @WithMockUser
        @DisplayName("다일리 페이지 생성 요청이 성공했을 때 응답값을 검사한다.")
        void test1() throws Exception {
            //given
            given(dailryPageService.create()).willReturn(비어있는_다일리_페이지_DTO());

            //when
            ResultActions perform = mockMvc.perform(post("/api/pages")
                    .with(csrf().asHeader())
            );

            //then
            perform.andExpect(status().isCreated())
                    .andDo(print())
                    .andExpect(jsonPath("$.dailryPageId").value(DAILRY_PAGE_ID))
                    .andExpect(jsonPath("$.background").value("grid"))
                    .andExpect(jsonPath("$.pageNumber").value(1));

            //restdocs
            perform.andDo(document("다일리 페이지 생성",
                    responseFields(
                            fieldWithPath("dailryPageId").type(NUMBER).description("다일리 페이지 id"),
                            fieldWithPath("background").type(STRING).description("페이지 배경"),
                            fieldWithPath("pageNumber").type(NUMBER).description("다일리 페이지 번호 (몇 페이지 인지)")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("updatePage() - 다일리 페이지 수정 메서드 테스트")
    class updatePage {
        @Test
        @WithMockUser
        @DisplayName("다일리 페이지 수정 요청이 성공했을 때 응답 값을 검사한다.")
        void test1() throws Exception {
            //given
            given(dailryPageService.update(any(), any(), any(), any())).willReturn(다일리_페이지_응답_DTO());

            //when
            ResultActions perform = mockMvc.perform(multipart("/api/dailry/pages/{pageId}/edit", DAILRY_PAGE_ID)
                    .file(다일리_페이지_섬네일_파일())
                    .file(다일리_페이지_요청_DTO_JSON_파일())
                    .contentType(MULTIPART_FORM_DATA)
                    .with(csrf().asHeader())
            );

            //then
            perform.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.dailryPageId").value(DAILRY_PAGE_ID))
                    .andExpect(jsonPath("$.background").value("무지"))
                    .andExpect(jsonPath("$.pageNumber").value(1))
                    .andExpect(jsonPath("$.thumbnail").value("https://data.da-ily.site/thumbnail/5/1/awefkaweop"))
                    .andExpect(jsonPath("$.elements").isNotEmpty());

            //restdocs
            perform.andDo(document("다일리 페이지 수정",
                    pathParameters(
                            parameterWithName("pageId").description("다일리 페이지 id")
                    ),
                    requestParts(
                            partWithName("thumbnail").description("다일리 페이지 섬네일 파일"),
                            partWithName("dailryPageRequest").description("다일리 페이지 수정 요청 데이터 (JSON)")
                    ),
                    relaxedRequestPartFields(
                            "dailryPageRequest",
                            fieldWithPath("background").type(STRING).description("다일리 페이지 배경"),
                            fieldWithPath("elements").type(ARRAY).description("페이지 elements 목록"),
                            fieldWithPath("elements[].id").type(STRING).description("element의 id"),
                            fieldWithPath("elements[].type").type(STRING).description("element의 type"),
                            fieldWithPath("elements[].order").type(NUMBER).description("element의 순서"),
                            fieldWithPath("elements[].position.x").type(NUMBER).description("element의 x좌표"),
                            fieldWithPath("elements[].position.y").type(NUMBER).description("element의 y좌표"),
                            fieldWithPath("elements[].size.width").type(NUMBER).description("element의 너비"),
                            fieldWithPath("elements[].size.height").type(NUMBER).description("element의 높이"),
                            fieldWithPath("elements[].rotation").type(STRING).description("element의 회전정보"),
                            fieldWithPath("elements[].typeContent").type(OBJECT).description("해당 element 타입별 개별속성")
                    ),
                    relaxedResponseFields(
                            fieldWithPath("dailryPageId").type(NUMBER).description("다일리 페이지 id"),
                            fieldWithPath("background").type(STRING).description("다일리 페이지 배경"),
                            fieldWithPath("pageNumber").type(NUMBER).description("다일리 페이지 번호 (몇 페이지 인지)"),
                            fieldWithPath("thumbnail").type(STRING).description("해당 페이지 썸네일 URL"),
                            fieldWithPath("elements").type(ARRAY).description("페이지 elements 목록"),
                            fieldWithPath("elements[].id").type(STRING).description("element의 id"),
                            fieldWithPath("elements[].type").type(STRING).description("element의 type"),
                            fieldWithPath("elements[].order").type(NUMBER).description("element의 순서"),
                            fieldWithPath("elements[].position.x").type(NUMBER).description("element의 x좌표"),
                            fieldWithPath("elements[].position.y").type(NUMBER).description("element의 y좌표"),
                            fieldWithPath("elements[].size.width").type(NUMBER).description("element의 너비"),
                            fieldWithPath("elements[].size.height").type(NUMBER).description("element의 높이"),
                            fieldWithPath("elements[].rotation").type(STRING).description("element의 회전정보"),
                            fieldWithPath("elements[].typeContent").type(OBJECT).description("해당 element 타입별 개별속성")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("findPage() - 다일리 페이지 단건 조회 메서드 테스트")
    class findPage {
        @Test
        @WithMockUser
        @DisplayName("다일리 페이지 조회 요청이 성공했을 때 응답 값을 검사한다.")
        void test1() throws Exception {
            //given
            given(dailryPageService.find(any(), any())).willReturn(다일리_페이지_응답_DTO());

            //when
            ResultActions perform = mockMvc.perform(get("/api/dailry/pages/{pageId}", DAILRY_PAGE_ID)
                    .with(csrf().asHeader())
            );

            //then
            perform.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.dailryPageId").value(DAILRY_PAGE_ID))
                    .andExpect(jsonPath("$.background").value("무지"))
                    .andExpect(jsonPath("$.pageNumber").value(1))
                    .andExpect(jsonPath("$.thumbnail").value("https://data.da-ily.site/thumbnail/5/1/awefkaweop"))
                    .andExpect(jsonPath("$.elements").isNotEmpty());
            
            //restdocs
            perform.andDo(document("다일리 페이지 단건 조회",
                    pathParameters(
                            parameterWithName("pageId").description("다일리 페이지 id")
                    ),
                    relaxedResponseFields(
                            fieldWithPath("dailryPageId").type(NUMBER).description("다일리 페이지 id"),
                            fieldWithPath("background").type(STRING).description("다일리 페이지 배경"),
                            fieldWithPath("pageNumber").type(NUMBER).description("다일리 페이지 번호 (몇 페이지 인지)"),
                            fieldWithPath("thumbnail").type(STRING).description("해당 페이지 썸네일 URL"),
                            fieldWithPath("elements").type(ARRAY).description("페이지 elements 목록"),
                            fieldWithPath("elements[].id").type(STRING).description("element의 id"),
                            fieldWithPath("elements[].type").type(STRING).description("element의 type"),
                            fieldWithPath("elements[].order").type(NUMBER).description("element의 순서"),
                            fieldWithPath("elements[].position.x").type(NUMBER).description("element의 x좌표"),
                            fieldWithPath("elements[].position.y").type(NUMBER).description("element의 y좌표"),
                            fieldWithPath("elements[].size.width").type(NUMBER).description("element의 너비"),
                            fieldWithPath("elements[].size.height").type(NUMBER).description("element의 높이"),
                            fieldWithPath("elements[].rotation").type(STRING).description("element의 회전정보"),
                            fieldWithPath("elements[].typeContent").type(OBJECT).description("해당 element 타입별 개별속성")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("findAllPage() - 다일리 페이지 전체 조회(썸네일, 페이지번호) 메서드 테스트")
    class findAllPage {
        @Test
        @WithMockUser
        @DisplayName("다일리 전체 페이지 조회가 성공했을 때 응답결과를 검사한다.")
        void test1() throws Exception {
            //given
            given(dailryPageService.findAll(any(), any())).willReturn(다일리_페이지_미리보기_DTO());

            //when
            ResultActions perform = mockMvc.perform(get("/api/dailry/{dailryId}/pages", DAILRY_PAGE_ID)
                    .with(csrf().asHeader())
            );

            //then
            perform.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.dailryId").value(2L))
                    .andExpect(jsonPath("$.pages").isArray());

            //restdocs
            perform.andDo(document("다일리 페이지 미리보기",
                    pathParameters(
                            parameterWithName("dailryId").description("다일리 id")
                    ),
                    responseFields(
                            fieldWithPath("dailryId").type(NUMBER).description("다일리 id"),
                            fieldWithPath("pages").type(ARRAY).description("미리보기 페이지 목록"),
                            fieldWithPath("pages[].pageNumber").type(NUMBER).description("다일리 페이지 번호 (몇 페이지 인지)"),
                            fieldWithPath("pages[].thumbnail").type(STRING).description("썸네일 이미지 URL")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("deletePage() - 다일리 페이지 삭제 메서드 테스트")
    class deletePage {
        @Test
        @WithMockUser
        @DisplayName("다일리 페이지 삭제가 성공했을 때 응답결과를 검사한다.")
        void test1() throws Exception {
            ResultActions perform = mockMvc.perform(delete("/api/dailry/pages/{pageId}", DAILRY_PAGE_ID)
                    .with(csrf().asHeader())
            );

            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.successful").value(true))
                    .andExpect(jsonPath("$.statusCode").value(200));

            //restdocs
            perform.andDo(RestDocsUtil.document("다일리 페이지 삭제",
                    pathParameters(
                            parameterWithName("pageId").description("다일리 페이지 id")
                    )
            ));
        }
    }
}