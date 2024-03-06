package com.daily.daily.post.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.post.dto.PostReadSliceResponseDTO;
import com.daily.daily.post.dto.PostWriteResponseDTO;
import com.daily.daily.post.service.PostService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.daily.daily.post.fixture.PostFixture.*;
import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @Test
    @DisplayName("게시글을 성공적으로 생성했을 경우 응답값을 검사한다.")
    @WithMockUser
    void createPost() throws Exception {
        //given
        given(postService.create(any(), any(), any())).willReturn(게시글_작성_응답_DTO());

        //when
        ResultActions perform = mockMvc.perform(multipart("/api/posts")
                .file(다일리_페이지_이미지_파일())
                .file(게시글_요청_DTO_JSON_파일())
                .contentType(MULTIPART_FORM_DATA)
                .with(csrf().asHeader())
        );

        //then
        String content = perform.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        PostWriteResponseDTO 실제_테스트_응답_결과 = objectMapper.readValue(content, PostWriteResponseDTO.class);
        assertThat(게시글_작성_응답_DTO()).isEqualTo(실제_테스트_응답_결과);

        //restdocs
        perform.andDo(document("게시글 작성",
                requestParts(
                        partWithName("pageImage").description("다일리 페이지 이미지 파일 (MIME 타입 'image/**' 만가능)"),
                        partWithName("request").description("게시글 작성 요청 데이터 (JSON)")
                ),
                requestPartFields(
                        "request",
                        fieldWithPath("content").description("게시글 본문 내용"),
                        fieldWithPath("hashtags").description("해시태그 목록 (없을시 '일반' 해시태그 추가)").optional()
                ),
                responseFields(
                        fieldWithPath("postId").type(NUMBER).description("게시글 id"),
                        fieldWithPath("content").type(STRING).description("게시글 본문 내용"),
                        fieldWithPath("pageImage").type(STRING).description("게시된 다일리 이미지 URL"),
                        fieldWithPath("hashtags").type(ARRAY).description("게시글 해시태그"),
                        fieldWithPath("writerId").type(NUMBER).description("게시글 작성자 고유 식별자"),
                        fieldWithPath("writerNickname").type(STRING).description("게시글 작성자 닉네임"),
                        fieldWithPath("createdTime").type(STRING).description("게시글 생성 시간")
                )
        ));
    }


    @Nested
    @DisplayName("updatePost() -게시글 수정 테스트")
    class updatePost {
        @Test
        @WithMockUser
        @DisplayName("게시글 수정 요청에 이미지 파일과, Json정보를 전부 보냈을 경우 응답값을 검사한다.")
        void imageAndJson() throws Exception {
            //given
            given(postService.update(any(), any(), any(), any())).willReturn(게시글_작성_응답_DTO());

            //when
            ResultActions perform = mockMvc.perform(multipart("/api/posts/{postId}/edit", POST_ID)
                    .file(다일리_페이지_이미지_파일())
                    .file(게시글_요청_DTO_JSON_파일())
                    .contentType(MULTIPART_FORM_DATA)
                    .with(csrf().asHeader())
            );

            //then
            String content = perform.andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

            PostWriteResponseDTO 실제_테스트_응답_결과 = objectMapper.readValue(content, PostWriteResponseDTO.class);
            assertThat(게시글_작성_응답_DTO()).isEqualTo(실제_테스트_응답_결과);

            //restdocs
            perform.andDo(document("게시글 수정",
                    pathParameters(
                            parameterWithName("postId").description("게시글 id")
                    ),
                    requestParts(
                            partWithName("pageImage").description("다일리 페이지 이미지 파일 (MIME 타입 'image/**' 만가능)"),
                            partWithName("request").description("게시글 수정 요청 데이터 (JSON)")
                    ),
                    requestPartFields(
                            "request",
                            fieldWithPath("content").description("게시글 본문 내용"),
                            fieldWithPath("hashtags").description("해시태그 목록 (없을시 '일반' 해시태그 추가)").optional()
                    ),
                    responseFields(
                            fieldWithPath("postId").type(NUMBER).description("게시글 id"),
                            fieldWithPath("content").type(STRING).description("게시글 본문 내용"),
                            fieldWithPath("pageImage").type(STRING).description("게시된 다일리 이미지 URL"),
                            fieldWithPath("hashtags").type(ARRAY).description("게시글 해시태그"),
                            fieldWithPath("writerId").type(NUMBER).description("게시글 작성자 고유 식별자"),
                            fieldWithPath("writerNickname").type(STRING).description("게시글 작성자 닉네임"),
                            fieldWithPath("createdTime").type(STRING).description("게시글 생성 시간")
                    )
            ));
        }

        @Test
        @WithMockUser
        @DisplayName("게시글 수정 요청에 이미지 파일을 보내지 않고, Json 정보만 보냈을 때에는 에러 없이 200 상태코드를 반환해야 한다.")
        void onlyJson() throws Exception {
            //given, when
            ResultActions perform = mockMvc.perform(multipart("/api/posts/{postId}/edit", POST_ID)
                    .file(게시글_요청_DTO_JSON_파일())
                    .contentType(MULTIPART_FORM_DATA)
                    .with(csrf().asHeader())
            );

            //then
            perform.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("게시글 수정 요청에 Json 정보를 보내지 않을 경우에는 400에러가 발생해야 한다.")
        void noneJson() throws Exception {
            //given, when
            ResultActions perform = mockMvc.perform(multipart("/api/posts/{postId}/edit", POST_ID)
                    .file(다일리_페이지_이미지_파일())
                    .contentType(MULTIPART_FORM_DATA)
                    .with(csrf().asHeader())
            );

            //then
            perform.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("deletePost() -게시글 삭제 테스트")
    class deletePost {
        @Test
        @WithMockUser
        @DisplayName("게시글 삭제가 성공했을 때 응답결과를 검사한다.")
        void test1() throws Exception {
            ResultActions perform = mockMvc.perform(delete("/api/posts/{postId}", 15L)
                    .with(csrf().asHeader())
            );

            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.successful").value(true))
                    .andExpect(jsonPath("$.statusCode").value(200));

            //restdocs
            perform.andDo(RestDocsUtil.document("게시글 삭제",
                    pathParameters(
                            parameterWithName("postId").description("게시글 id")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("readSinglePost() -게시글 단건 조회 테스트")
    class readSinglePost {
        @Test
        @WithMockUser
        @DisplayName("게시글 단건 조회가 성공했을 때 응답결과를 검사한다.")
        void test1() throws Exception {
            given(postService.find(31L)).willReturn(게시글_단건_조회_DTO());

            ResultActions perform = mockMvc.perform(get("/api/posts/{postId}", POST_ID)
                    .with(csrf().asHeader())
            );

            String content = perform.andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

            PostWriteResponseDTO 테스트_조회_결과 = objectMapper.readValue(content, PostWriteResponseDTO.class);
            assertThat(게시글_작성_응답_DTO()).isEqualTo(테스트_조회_결과);
            
            //restdocs
            perform.andDo(RestDocsUtil.document("게시글 단건 조회",
                    pathParameters(
                            parameterWithName("postId").description("게시글 id")
                    ),
                    responseFields(
                            fieldWithPath("postId").type(NUMBER).description("게시글 id"),
                            fieldWithPath("content").type(STRING).description("게시글 본문 내용"),
                            fieldWithPath("pageImage").type(STRING).description("게시된 다일리 이미지 URL"),
                            fieldWithPath("hashtags").type(ARRAY).description("게시글 해시태그"),
                            fieldWithPath("writerId").type(NUMBER).description("게시글 작성자 고유 식별자"),
                            fieldWithPath("writerNickname").type(STRING).description("게시글 작성자 닉네임"),
                            fieldWithPath("likeCount").type(NUMBER).description("좋아요 숫자"),
                            fieldWithPath("createdTime").type(STRING).description("게시글 생성 시간")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("readSlicePost() - 게시글 무한스크롤 조회 테스트")
    class readSlicePost {
        @Test
        @WithMockUser
        @DisplayName("게시글 Slice 조회가 성공했을 때 응답 결과를 검사한다.")
        void test1() throws Exception {

            PostReadSliceResponseDTO 게시글_여러건_조회_dto = 게시글_여러건_조회_DTO();
            given(postService.findSlice(PageRequest.of(0, 5))).willReturn(게시글_여러건_조회_dto);


            ResultActions perform = mockMvc.perform(get("/api/posts")
                    .with(csrf().asHeader())
                    .queryParam("page", "0")
                    .queryParam("size", "5")
            );


            String content = perform.andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

            PostReadSliceResponseDTO 테스트_조회_결과 = objectMapper.readValue(content, PostReadSliceResponseDTO.class);
            assertThat(테스트_조회_결과).usingRecursiveComparison().isEqualTo(게시글_여러건_조회_dto);

            //restdocs
            perform.andDo(RestDocsUtil.document("게시글 여러건 조회",
                    queryParameters(
                            parameterWithName("page").description("페이지 번호(0부터 시작)"),
                            parameterWithName("size").description("페이지 사이즈")
                    ),
                    responseFields(
                            fieldWithPath("presentPage").type(NUMBER).description("현재 페이지 번호"),
                            fieldWithPath("hasNext").type(BOOLEAN).description("다음 게시글이 존재하는지 유무"),
                            fieldWithPath("posts").type(ARRAY).description("게시글 목록 배열"),
                            fieldWithPath("posts[].postId").type(NUMBER).description("게시글 id"),
                            fieldWithPath("posts[].content").type(STRING).description("게시글 본문 내용"),
                            fieldWithPath("posts[].pageImage").type(STRING).description("게시된 다일리 이미지 URL"),
                            fieldWithPath("posts[].hashtags").type(ARRAY).description("게시글 해시태그"),
                            fieldWithPath("posts[].writerId").type(NUMBER).description("게시글 작성자 고유 식별자"),
                            fieldWithPath("posts[].writerNickname").type(STRING).description("게시글 작성자 닉네임"),
                            fieldWithPath("posts[].likeCount").type(NUMBER).description("좋아요 숫자"),
                            fieldWithPath("posts[].createdTime").type(STRING).description("게시글 생성 시간")
                    )
            ));
        }
    }

    @Nested
    @DisplayName("readPostByHashTag() - 해시태그 검색을 통한 게시글 조회 테스트")
    class readPostByHashTag {
        @Test
        @WithMockUser
        @DisplayName("해시태그를 검색할경우 해당 해시태그를 포함하는 게시글 조회가 성공했을 때 응답 결과를 검사한다.")
        void test1() throws Exception {
            //given
            PostReadSliceResponseDTO 게시글_해시태그로_조회_dto = 게시글_해시태그로_조회_DTO();
            given(postService.findPostByHashtag(List.of("대학생", "시험기간"), PageRequest.of(0, 2))).willReturn(게시글_해시태그로_조회_dto);

            //when
            ResultActions perform = mockMvc.perform(get("/api/posts/hashtags")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(회원이_검색한_해시태그()))
                    .with(csrf().asHeader())
                    .queryParam("page", "0")
                    .queryParam("size", "2")
            );

            String content = perform.andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

            PostReadSliceResponseDTO 테스트_조회_결과 = objectMapper.readValue(content, PostReadSliceResponseDTO.class);
            assertThat(테스트_조회_결과).usingRecursiveComparison().isEqualTo(게시글_해시태그로_조회_dto);

            //restdocs
            perform.andDo(RestDocsUtil.document("게시글 해시태그로 조회",
                    requestFields(
                            fieldWithPath("hashtags").description("게시글 해시태그")
                    ),
                    queryParameters(
                            parameterWithName("page").description("페이지 번호(0부터 시작)"),
                            parameterWithName("size").description("페이지 사이즈")
                    ),
                    responseFields(
                            fieldWithPath("presentPage").type(NUMBER).description("현재 페이지 번호"),
                            fieldWithPath("hasNext").type(BOOLEAN).description("다음 게시글이 존재하는지 유무"),
                            fieldWithPath("posts").type(ARRAY).description("게시글 목록 배열"),
                            fieldWithPath("posts[].postId").type(NUMBER).description("게시글 id"),
                            fieldWithPath("posts[].content").type(STRING).description("게시글 본문 내용"),
                            fieldWithPath("posts[].pageImage").type(STRING).description("게시된 다일리 이미지 URL"),
                            fieldWithPath("posts[].hashtags").type(ARRAY).description("게시글 해시태그"),
                            fieldWithPath("posts[].writerId").type(NUMBER).description("게시글 작성자 고유 식별자"),
                            fieldWithPath("posts[].writerNickname").type(STRING).description("게시글 작성자 닉네임"),
                            fieldWithPath("posts[].likeCount").type(NUMBER).description("좋아요 숫자"),
                            fieldWithPath("posts[].createdTime").type(STRING).description("게시글 생성 시간")
                    )
            ));
        }
    }
}