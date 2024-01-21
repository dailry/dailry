package com.daily.daily.postcomment.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import com.daily.daily.postcomment.service.PostCommentService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_생성_DTO;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_응답_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostCommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class PostCommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostCommentService postCommentService;

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
            given(postCommentService.create(any(), any(), any())).willReturn(response);

            ResultActions perform = mockMvc.perform(post("/api/posts/31/comments")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(댓글_생성_DTO()))
                    .with(csrf().asHeader())
            );
            //then
            perform.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.commentId").value(response.getCommentId()))
                    .andExpect(jsonPath("$.postId").value(response.getPostId()))
                    .andExpect(jsonPath("$.writerId").value(response.getWriterId()))
                    .andExpect(jsonPath("$.content").value(response.getContent()))
                    .andExpect(jsonPath("$.writerNickname").value(response.getWriterNickname()))
                    .andExpect(jsonPath("$.createdTime").value(response.getCreatedTime().toString()));
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
            PostCommentResponseDTO response = 댓글_응답_DTO();
            given(postCommentService.update(any(), any(), any())).willReturn(response);

            ResultActions perform = mockMvc.perform(patch("/api/comments/16")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(댓글_생성_DTO()))
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
        }
    }

}