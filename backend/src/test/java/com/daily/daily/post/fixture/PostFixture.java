package com.daily.daily.post.fixture;

import com.daily.daily.post.dto.PostRequestDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import org.springframework.mock.web.MockMultipartFile;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class PostFixture {

    //--------------------픽스쳐 객체--------------------//

    public static final PostRequestDTO 게시글_요청_DTO;
    public static final PostResponseDTO 게시글_응답_DTO;
    public static final MockMultipartFile 다일리_페이지_이미지_파일;
    public static final MockMultipartFile 게시글_요청_DTO_JSON_파일;










    //----------픽스쳐 객체 초기화에 필요한 상수 값------------//
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final Long POST_ID = 31L;
    private static final String POST_CONTENT = "오늘 저의 다일리입니다.";
    private static final String PAGE_IMAGE_URL = "imageURL";
    private static final LocalDateTime POST_CREATED_TIME = LocalDateTime.of(2024,1,18,15,38,32,42);

    //-------------------픽스쳐 객체 초기화------------------//

    static {
        게시글_요청_DTO = new PostRequestDTO(POST_CONTENT);
    }

    static {
        게시글_응답_DTO = PostResponseDTO.builder()
                .postId(POST_ID)
                .content(POST_CONTENT)
                .pageImage(PAGE_IMAGE_URL)
                .memberId(15L)
                .writer("배부른낙타")
                .createdTime(POST_CREATED_TIME)
                .build();
    }

    static {
        다일리_페이지_이미지_파일 = new MockMultipartFile(
                "pageImage",
                "dailryPage.png",
                IMAGE_PNG_VALUE,
                "".getBytes()
        );
    }

    static {
        try {
            게시글_요청_DTO_JSON_파일 = new MockMultipartFile(
                    "request",
                    "request",
                    APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(게시글_요청_DTO)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
