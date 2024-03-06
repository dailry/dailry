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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
        }
    }
}