package com.daily.daily.post.fixture;

import com.daily.daily.member.domain.Member;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.dto.PostRequestDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static com.daily.daily.member.fixture.MemberFixture.일반회원;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class PostFixture {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final Long POST_ID = 31L;
    private static final String POST_CONTENT = "오늘 저의 다일리입니다.";
    private static final String PAGE_IMAGE_URL = "imageURL";
    private static final LocalDateTime POST_CREATED_TIME = LocalDateTime.of(2024,1,18,15,38,32,42);


    public static PostRequestDTO 게시글_요청_DTO() {
        return new PostRequestDTO(POST_CONTENT);
    }

    public static PostResponseDTO 게시글_응답_DTO() {
        Member member = 일반회원();
        return PostResponseDTO.builder()
                .postId(POST_ID)
                .content(POST_CONTENT)
                .pageImage(PAGE_IMAGE_URL)
                .memberId(member.getId())
                .writer(member.getNickname())
                .createdTime(POST_CREATED_TIME)
                .build();
    }

    public static MockMultipartFile 다일리_페이지_이미지_파일() {
        return new MockMultipartFile(
                "pageImage",
                "dailryPage.png",
                IMAGE_PNG_VALUE,
                "dailryPage".getBytes()
        );
    }

    public static MockMultipartFile 게시글_요청_DTO_JSON_파일() throws JsonProcessingException {
        return new MockMultipartFile(
                "request",
                "request",
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(게시글_요청_DTO())
        );
    }
    public static Post 게시글(Member member) {
        return Post.builder()
                .id(3L)
                .content(POST_CONTENT)
                .pageImage(PAGE_IMAGE_URL)
                .postWriter(member)
                .build();
    }

    public static Post 일반회원이_작성한_게시글() {
        return Post.builder()
                .id(3L)
                .content(POST_CONTENT)
                .pageImage(PAGE_IMAGE_URL)
                .postWriter(일반회원())
                .build();
    }
}
