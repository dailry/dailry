package com.daily.daily.post.fixture;

import com.daily.daily.member.domain.Member;
import com.daily.daily.post.domain.Hashtag;
import com.daily.daily.post.domain.HotHashtag;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostHashtag;
import com.daily.daily.post.dto.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.daily.daily.member.fixture.MemberFixture.일반회원1;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class PostFixture {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final Long POST_ID = 31L;
    private static final String POST_CONTENT = "오늘 저의 다일리입니다.";
    private static final String PAGE_IMAGE_URL = "imageURL";
    public static final LocalDateTime POST_CREATED_TIME = LocalDateTime.of(2024,1,18,15,38,32,42);
    private static final List<String> HASHTAG_NAMES = List.of("일반", "음식", "대학생");
    private static final List<Hashtag> HASHTAGS = HASHTAG_NAMES.stream()
            .map(Hashtag::of)
            .toList();

    public static PostWriteRequestDTO 게시글_요청_DTO() {
        return new PostWriteRequestDTO(POST_CONTENT, new HashSet<>(HASHTAG_NAMES));
    }

    public static PostWriteResponseDTO 게시글_작성_응답_DTO() {
        Member member = 일반회원1();

        return PostWriteResponseDTO.builder()
                .postId(POST_ID)
                .content(POST_CONTENT)
                .pageImage(PAGE_IMAGE_URL)
                .writerId(member.getId())
                .writerNickname(member.getNickname())
                .hashtags(HASHTAG_NAMES)
                .createdTime(POST_CREATED_TIME)
                .build();
    }

    public static PostReadResponseDTO 게시글_단건_조회_DTO() {
        PostWriteResponseDTO 게시글_작성_응답_DTO = 게시글_작성_응답_DTO();

        return PostReadResponseDTO.builder()
                .postId(게시글_작성_응답_DTO.getPostId())
                .content(게시글_작성_응답_DTO.getContent())
                .pageImage(게시글_작성_응답_DTO.getPageImage())
                .writerId(게시글_작성_응답_DTO.getWriterId())
                .writerNickname(게시글_작성_응답_DTO.getWriterNickname())
                .hashtags(게시글_작성_응답_DTO.getHashtags())
                .createdTime(게시글_작성_응답_DTO.getCreatedTime())
                .likeCount(10L)
                .build();
    }

    public static PostReadSliceResponseDTO 게시글_여러건_조회_DTO() {
        PostReadResponseDTO 게시글1 = PostReadResponseDTO.builder()
                .postId(36L)
                .content("오늘 다일리 어떤가요?")
                .pageImage("https://imageURL123.com")
                .writerId(3L)
                .writerNickname("신입생123")
                .hashtags(List.of("대학생", "시험기간"))
                .createdTime(POST_CREATED_TIME)
                .likeCount(5L)
                .build();

        PostReadResponseDTO 게시글2 = PostReadResponseDTO.builder()
                .postId(37L)
                .content("오늘 다일리 어떤가요~~")
                .pageImage("https://imageURL13.com")
                .writerId(4L)
                .writerNickname("다솔맘")
                .hashtags(List.of("육아"))
                .createdTime(POST_CREATED_TIME)
                .likeCount(8L)
                .build();

        PostReadResponseDTO 게시글3 = PostReadResponseDTO.builder()
                .postId(38L)
                .content("오늘 업무량 실화냐..")
                .pageImage("https://imageURL123.com")
                .writerId(31L)
                .writerNickname("다일리개발자")
                .hashtags(List.of("개발자", "회사", "퇴사"))
                .createdTime(POST_CREATED_TIME)
                .likeCount(33L)
                .build();

        PostReadResponseDTO 게시글4 = PostReadResponseDTO.builder()
                .postId(39L)
                .content("주말 다일리 어떤가요?")
                .pageImage("https://imageURL1413.com")
                .writerId(83L)
                .writerNickname("주말좋아")
                .hashtags(List.of("주말", "휴식"))
                .createdTime(POST_CREATED_TIME)
                .likeCount(23L)
                .build();

        PostReadResponseDTO 게시글5 = PostReadResponseDTO.builder()
                .postId(40L)
                .content("유럽여행 25일차 일정 짜봤습니다.")
                .pageImage("https://imageURL23123.com")
                .writerId(512L)
                .writerNickname("여행유튜버")
                .hashtags(List.of("여행", "유럽여행"))
                .createdTime(POST_CREATED_TIME)
                .likeCount(212L)
                .build();

        return PostReadSliceResponseDTO
                .builder()
                .hasNext(true)
                .presentPage(7)
                .posts(List.of(게시글1, 게시글2, 게시글3, 게시글4, 게시글5))
                .build();
    }

    public static PostReadSliceResponseDTO 게시글_해시태그로_조회_DTO() {
        PostReadResponseDTO 게시글1 = PostReadResponseDTO.builder()
                .postId(36L)
                .content("오늘 다일리 어떤가요?")
                .pageImage("https://imageURL123.com")
                .writerId(3L)
                .writerNickname("신입생123")
                .hashtags(List.of("대학생", "시험기간"))
                .createdTime(POST_CREATED_TIME)
                .likeCount(5L)
                .build();

        PostReadResponseDTO 게시글2 = PostReadResponseDTO.builder()
                .postId(38L)
                .content("과제 실화냐..")
                .pageImage("https://imageURL123.com")
                .writerId(31L)
                .writerNickname("다일리개발자")
                .hashtags(List.of("대학생", "시험기간", "과제"))
                .createdTime(POST_CREATED_TIME)
                .likeCount(33L)
                .build();

        return PostReadSliceResponseDTO
                .builder()
                .hasNext(true)
                .presentPage(7)
                .posts(List.of(게시글1, 게시글2))
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

    public static Post 일반회원1이_작성한_게시글() {
        Post post = Post.builder()
                .content(POST_CONTENT)
                .pageImage(PAGE_IMAGE_URL)
                .postWriter(일반회원1())
                .build();

        HASHTAGS.forEach(hashtag -> post.addPostHashtag(PostHashtag.of(post, hashtag)));
        ReflectionTestUtils.setField(post, "id", POST_ID);
        return post;
    }

    public static HotHashtagReadListResponseDTO 핫한_해시태그_조회_DTO() {
        List<HotHashtag> hotHashtags = new ArrayList<>();

        Hashtag hashtag1 = Hashtag.of("대학생");
        Hashtag hashtag2 = Hashtag.of("친구");
        Hashtag hashtag3 = Hashtag.of("여행");

        hotHashtags.add(HotHashtag.of(10L, hashtag1));
        hotHashtags.add(HotHashtag.of(20L, hashtag2));
        hotHashtags.add(HotHashtag.of(30L, hashtag3));

        return HotHashtagReadListResponseDTO.from(hotHashtags);
    }
}
