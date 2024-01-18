package com.daily.daily.post.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.post.dto.PostRequestDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import com.daily.daily.post.fixture.PostFixture;
import com.daily.daily.post.service.PostService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static com.daily.daily.post.fixture.PostFixture.게시글_요청_DTO_JSON_파일;
import static com.daily.daily.post.fixture.PostFixture.게시글_응답_DTO;
import static com.daily.daily.post.fixture.PostFixture.다일리_페이지_이미지_파일;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
        given(postService.create(any(), any(), any())).willReturn(게시글_응답_DTO);

        //when
        ResultActions perform = mockMvc.perform(multipart("/api/posts")
                .file(다일리_페이지_이미지_파일)
                .file(게시글_요청_DTO_JSON_파일)
                .contentType(MULTIPART_FORM_DATA)
                .with(csrf().asHeader())
        );

        //then
        String content = perform.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        PostResponseDTO 테스트_응답_결과 = objectMapper.readValue(content, PostResponseDTO.class);
        assertThat(게시글_응답_DTO).isEqualTo(테스트_응답_결과);
    }
}