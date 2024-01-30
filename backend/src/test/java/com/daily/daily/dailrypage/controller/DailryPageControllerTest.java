package com.daily.daily.dailrypage.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.dailrypage.fixture.DailryPageFixture;
import com.daily.daily.dailrypage.service.DailryPageService;
import com.daily.daily.post.controller.PostController;
import com.daily.daily.testutil.document.RestDocsUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.daily.daily.dailrypage.fixture.DailryPageFixture.비어있는_다일리_페이지_DTO;
import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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

    @Nested
    @DisplayName("createPage() - 다일리 페이지 생성 메서드 테스트")
    class createPage {

        @Test
        @WithMockUser
        @DisplayName("다일리 페이지 생성 요청을 보냈을 때, 응답값을 검사한다.")
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
                    .andExpect(jsonPath("$.dailryPageId").value(5L))
                    .andExpect(jsonPath("$.background").value("grid"))
                    .andExpect(jsonPath("$.pageNumber").value(1));

            //restdocs
            perform.andDo(document("다일리 페이지 생성",
                    responseFields(
                            fieldWithPath("dailryPageId").type(NUMBER).description("다일리 페이지 id"),
                            fieldWithPath("background").type(STRING).description("페이지 배경"),
                            fieldWithPath("pageNumber").type(NUMBER).description("페이지 번호")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("updatePage() - 다일리 페이지 수정 메서드 테스트")
    class updatePage {
        @Test
        @WithMockUser
        @DisplayName("다일리 페이지 수정 요청을 보냈을 때, 응답 값을 검사한다.")
        void test1() {
//            given(dailryPageService.update(5L, ))
        }
    }

    @Nested
    @DisplayName("findPage() - 다일리 페이지 조회 메서드 테스트")
    class findPage {

    }

    @Nested
    @DisplayName("findAllPage() - 다일리 페이지 전체 조회(썸네일, 페이지번호) 메서드 테스트")
    class findAllPage {

    }

    @Nested
    @DisplayName("deletePage() - 다일리 페이지 삭제 메서드 테스트")
    class deletePage {

    }
}