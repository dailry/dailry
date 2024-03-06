package com.daily.daily.post.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.post.dto.HotPostReadSliceResponseDTO;
import com.daily.daily.post.fixture.HotPostFixture;
import com.daily.daily.post.service.HotPostService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = HotPostController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@AutoConfigureRestDocs
class HotPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    HotPostService hotPostService;

    @Nested
    @DisplayName("readSlice() - 인기 게시글 여러건 조회기능 테스트")
    class readSlice{
        @Test
        @DisplayName("인기게시글을 여러건 조회가 성공했을 때 응답 결과를 검사한다.")
        @WithMockUser
        void test1() throws Exception {
            //given
            HotPostReadSliceResponseDTO 인기_게시글_여러건_조회_DTO = HotPostFixture.인기_게시글_여러건_조회_DTO();
            given(hotPostService.findSlice(PageRequest.of(0,5))).willReturn(인기_게시글_여러건_조회_DTO);

            //when
            ResultActions perform = mockMvc.perform(get("/api/hotPosts")
                    .with(csrf().asHeader())
                    .queryParam("page", "0")
                    .queryParam("size", "5")
            );

            //then
            String content = perform.andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

            HotPostReadSliceResponseDTO 테스트_조회_결과 = objectMapper.readValue(content, HotPostReadSliceResponseDTO.class);
            assertThat(테스트_조회_결과).isEqualTo(인기_게시글_여러건_조회_DTO);
            
            //restdocs
            perform.andDo(document("인기 게시글 여러건 조회",
                    queryParameters(
                            parameterWithName("page").description("페이지 번호(0부터 시작)"),
                            parameterWithName("size").description("페이지별 게시글 갯수")
                    ),
                    responseFields(
                            fieldWithPath("presentPage").type(NUMBER).description("현재 페이지 번호"),
                            fieldWithPath("hasNext").type(BOOLEAN).description("다음 게시글이 존재하는지 유무"),
                            fieldWithPath("hotPosts").type(ARRAY).description("인기 게시글 목록 배열"),
                            fieldWithPath("hotPosts[].postId").type(NUMBER).description("게시글 id"),
                            fieldWithPath("hotPosts[].content").type(STRING).description("게시글 본문 내용"),
                            fieldWithPath("hotPosts[].pageImage").type(STRING).description("게시된 다일리 이미지 URL"),
                            fieldWithPath("hotPosts[].hashtags").type(ARRAY).description("게시글 해시태그"),
                            fieldWithPath("hotPosts[].writerId").type(NUMBER).description("게시글 작성자 고유 식별자"),
                            fieldWithPath("hotPosts[].writerNickname").type(STRING).description("게시글 작성자 닉네임"),
                            fieldWithPath("hotPosts[].likeCount").type(NUMBER).description("좋아요 숫자"),
                            fieldWithPath("hotPosts[].createdTime").type(STRING).description("게시글 생성 시간"),
                            fieldWithPath("hotPosts[].hotPostCreatedTime").type(STRING).description("인기 게시글로 올라간 시간")
                    )
            ));
        }
    }
}