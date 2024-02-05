package com.daily.daily.postcomment.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.postcomment.dto.PostCommentSliceDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import com.daily.daily.postcomment.service.PostCommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.daily.daily.post.fixture.PostFixture.POST_ID;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.COMMENT_ID;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_생성_DTO;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_수정_DTO;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_응답_DTO;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_조회_페이징_DTO;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.수정된_댓글_응답_DTO;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.요청_페이지_사이즈;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.요청_페이지_숫자;
import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostCommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@AutoConfigureRestDocs
class PostCommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostCommentService commentService;

    @Autowired
    ObjectMapper objectMapper;
    @Nested
    @DisplayName("create() - 댓글 생성 메서드 테스트")
    class create {

        @Test
        @DisplayName("댓글을 성공적으로 생성했을 때 응답결과를 테스트한다.")
        @WithMockUser
        void test1() throws Exception {
            //given, when
            PostCommentResponseDTO response = 댓글_응답_DTO();
            given(commentService.create(any(), any(), any())).willReturn(response);

            ResultActions perform = mockMvc.perform(post("/api/posts/{postId}/comments", POST_ID)
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(댓글_생성_DTO()))
                    .with(csrf().asHeader())
            );
            //then
            perform.andExpect(status().isCreated())
                    .andDo(print())
                    .andExpect(jsonPath("$.commentId").value(response.getCommentId()))
                    .andExpect(jsonPath("$.postId").value(response.getPostId()))
                    .andExpect(jsonPath("$.writerId").value(response.getWriterId()))
                    .andExpect(jsonPath("$.content").value(response.getContent()))
                    .andExpect(jsonPath("$.writerNickname").value(response.getWriterNickname()))
                    .andExpect(jsonPath("$.createdTime").value(response.getCreatedTime().toString()));

            //restdocs
            perform.andDo(document("댓글 작성",
                    pathParameters(
                            parameterWithName("postId").description("게시글 id")
                    ),
                    requestFields(
                            fieldWithPath("content").type(STRING).description("댓글 내용")
                    ), responseFields(
                            fieldWithPath("commentId").type(NUMBER).description("댓글 id"),
                            fieldWithPath("postId").type(NUMBER).description("댓글을 작성한 게시글의 id"),
                            fieldWithPath("writerId").type(NUMBER).description("댓글작성자의 id"),
                            fieldWithPath("content").type(STRING).description("댓글 내용"),
                            fieldWithPath("writerNickname").type(STRING).description("댓글 작성자의 닉네임"),
                            fieldWithPath("createdTime").type(STRING).description("댓글 작성 시간")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("update() - 댓글 수정 메서드 테스트")
    class update {

        @Test
        @DisplayName("댓글을 성공적으로 수정했을 때 응답결과를 테스트한다.")
        @WithMockUser
        void test1() throws Exception {
            //given, when
            PostCommentResponseDTO response = 수정된_댓글_응답_DTO();
            given(commentService.update(any(), any(), any())).willReturn(response);

            ResultActions perform = mockMvc.perform(patch("/api/posts/comments/{commentId}", COMMENT_ID)
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(댓글_수정_DTO()))
                    .with(csrf().asHeader())
            );
            //then
            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.commentId").value(response.getCommentId()))
                    .andExpect(jsonPath("$.postId").value(response.getPostId()))
                    .andExpect(jsonPath("$.writerId").value(response.getWriterId()))
                    .andExpect(jsonPath("$.content").value(response.getContent()))
                    .andExpect(jsonPath("$.writerNickname").value(response.getWriterNickname()))
                    .andExpect(jsonPath("$.createdTime").value(response.getCreatedTime().toString()));

            //restdocs
            perform.andDo(document("댓글 수정",
                    pathParameters(
                            parameterWithName("commentId").description("수정할 댓글 id")
                    ),
                    requestFields(
                            fieldWithPath("content").type(STRING).description("수정할 댓글 내용")
                    ),
                    responseFields(
                            fieldWithPath("commentId").type(NUMBER).description("댓글 id"),
                            fieldWithPath("postId").type(NUMBER).description("댓글을 작성한 게시글의 id"),
                            fieldWithPath("writerId").type(NUMBER).description("댓글작성자의 id"),
                            fieldWithPath("content").type(STRING).description("수정한 댓글 내용"),
                            fieldWithPath("writerNickname").type(STRING).description("댓글 작성자의 닉네임"),
                            fieldWithPath("createdTime").type(STRING).description("댓글 작성 시간")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("delete() - 댓글 삭제 메서드 테스트")
    class delete {

        @Test
        @DisplayName("댓글을 성공적으로 삭제했을 때 응답결과를 테스트한다.")
        @WithMockUser
        void test1() throws Exception {
            //given, when
            ResultActions perform = mockMvc.perform(delete("/api/posts/comments/{commentId}", COMMENT_ID)
                    .with(csrf().asHeader())
            );
            //then
            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.successful").value(true))
                    .andExpect(jsonPath("$.statusCode").value(200));

            //restdocs
            perform.andDo(document("댓글 삭제",
                    pathParameters(
                            parameterWithName("commentId").description("삭제할 댓글 id")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("readByPostId() - 댓글 조회 메서드 테스트")
    class readByPostId {
        @Test
        @DisplayName("댓글을 성공적으로 조회했을 때 응답결과를 테스트한다.")
        @WithMockUser
        void test1() throws Exception{
            //given
            PostCommentSliceDTO 응답결과 = 댓글_조회_페이징_DTO();

            given(commentService.readByPostId(any(), any())).willReturn(응답결과);

            //when
            ResultActions perform = mockMvc.perform(get("/api/posts/{postId}/comments", POST_ID)
                    .queryParam("page", String.valueOf(요청_페이지_숫자))
                    .queryParam("size", String.valueOf(요청_페이지_사이즈))
                    .with(csrf().asHeader())
            );

            //then
            perform.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.presentPage").value(응답결과.getPresentPage()))
                    .andExpect(jsonPath("$.hasNext").value(응답결과.isHasNext()))
                    .andExpect(jsonPath("$.comments").isArray())
                    .andExpect(jsonPath("$.comments", Matchers.hasSize(요청_페이지_사이즈)));

            //restdocs
            perform.andDo(document("댓글 조회",
                    pathParameters(
                            parameterWithName("postId").description("게시글 id")
                    ),
                    queryParameters(
                            parameterWithName("page").description("페이지 번호(0부터 시작)"),
                            parameterWithName("size").description("페이지 사이즈")
                    ),
                    responseFields(
                            fieldWithPath("presentPage").type(NUMBER).description("현재 페이지 번호"),
                            fieldWithPath("hasNext").type(BOOLEAN).description("다음 댓글이 존재하는지 유무"),
                            fieldWithPath("comments").type(ARRAY).description("댓글 목록 배열"),
                            fieldWithPath("comments[].commentId").type(NUMBER).description("댓글 id"),
                            fieldWithPath("comments[].content").type(STRING).description("댓글 내용"),
                            fieldWithPath("comments[].writerNickname").type(STRING).description("댓글 작성자 닉네임"),
                            fieldWithPath("comments[].createdTime").type(STRING).description("댓글 생성 시간")
                    )
            ));
        }
    }
}