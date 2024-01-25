package com.daily.daily.post.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.post.dto.PostWriteResponseDTO;
import com.daily.daily.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
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

import java.nio.charset.StandardCharsets;

import static com.daily.daily.post.fixture.PostFixture.게시글_단건_조회_DTO;
import static com.daily.daily.post.fixture.PostFixture.게시글_요청_DTO_JSON_파일;
import static com.daily.daily.post.fixture.PostFixture.게시글_작성_응답_DTO;
import static com.daily.daily.post.fixture.PostFixture.다일리_페이지_이미지_파일;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@MockBean(JpaMetamodelMappingContext.class)
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
    }


    @Nested
    @DisplayName("updatePost() -게시글 수정 테스트")
    class updatePost {
        @Test
        @WithMockUser
        @DisplayName("게시글 수정 요청에 이미지 파일과, Json정보를 전부 보냈을 경우 응답값을 검사한다.")
        void imageAndJson() throws Exception {
            //given
            given(postService.update(any(), any(), any())).willReturn(게시글_작성_응답_DTO());

            //when
            ResultActions perform = mockMvc.perform(multipart("/api/posts/31/edit")
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
        }

        @Test
        @WithMockUser
        @DisplayName("게시글 수정 요청에 이미지 파일을 보내지 않고, Json 정보만 보냈을 때에는 에러 없이 200 상태코드를 반환해야 한다.")
        void onlyJson() throws Exception {
            //given, when
            ResultActions perform = mockMvc.perform(multipart("/api/posts/31/edit")
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
            ResultActions perform = mockMvc.perform(multipart("/api/posts/31/edit")
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
            ResultActions perform = mockMvc.perform(delete("/api/posts/15")
                    .with(csrf().asHeader())
            );

            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.successful").value(true))
                    .andExpect(jsonPath("$.statusCode").value(200));
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

            ResultActions perform = mockMvc.perform(get("/api/posts/31")
                    .with(csrf().asHeader())
            );

            String content = perform.andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

            PostWriteResponseDTO 테스트_조회_결과 = objectMapper.readValue(content, PostWriteResponseDTO.class);
            Assertions.assertThat(게시글_작성_응답_DTO()).isEqualTo(테스트_조회_결과);
        }
    }
}