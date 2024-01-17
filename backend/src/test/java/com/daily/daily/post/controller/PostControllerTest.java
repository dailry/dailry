package com.daily.daily.post.controller;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import com.daily.daily.post.dto.PostCreateDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import com.daily.daily.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        PostCreateDTO postCreateDTO = new PostCreateDTO("오늘 저의 다일리입니다.");
        PostResponseDTO postResponseDTO = PostResponseDTO.builder()
                .postId(31L)
                .content("오늘 저의 다일리입니다.")
                .pageImage("imageURL")
                .memberId(15L)
                .nickname("배부른낙타")
                .createdTime(LocalDateTime.now())
                .build();

        given(postService.create(any(), any(), any())).willReturn(postResponseDTO);

        MockMultipartFile pageImageFile = new MockMultipartFile(
                "pageImage",
                "dailryPage.png",
                APPLICATION_JSON_VALUE,
                "".getBytes()
        );

        MockMultipartFile multipartJson = new MockMultipartFile(
                "request",
                "request",
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(postCreateDTO)
        );

        //when
        ResultActions perform = mockMvc.perform(multipart("/api/posts")
                .file(pageImageFile)
                .file(multipartJson)
                .contentType(MULTIPART_FORM_DATA)
                .with(csrf().asHeader())
        );

        //then
        String content = perform.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        PostResponseDTO testResponse = objectMapper.readValue(content, PostResponseDTO.class);
        assertThat(postResponseDTO).isEqualTo(testResponse);
    }
}