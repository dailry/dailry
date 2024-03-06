package com.daily.daily.post.fixture;

import com.daily.daily.post.dto.HotPostReadResponseDTO;
import com.daily.daily.post.dto.HotPostReadSliceResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

import static com.daily.daily.post.fixture.PostFixture.POST_CREATED_TIME;


public class HotPostFixture {

    private static final LocalDateTime HOT_POST_CREATED_TIME =  LocalDateTime.of(2024,3,6,17,30,32,42);
    public static HotPostReadSliceResponseDTO 인기_게시글_여러건_조회_DTO() {
        HotPostReadResponseDTO 인기게시글1 = HotPostReadResponseDTO.builder()
                .postId(36L)
                .content("오늘 다일리 어떤가요?")
                .pageImage("https://imageURL123.com")
                .writerId(3L)
                .writerNickname("신입생123")
                .hashtags(List.of("대학생", "시험기간"))
                .createdTime(POST_CREATED_TIME)
                .hotPostCreatedTime(HOT_POST_CREATED_TIME)
                .likeCount(5L)
                .build();

        HotPostReadResponseDTO 인기게시글2 = HotPostReadResponseDTO.builder()
                .postId(37L)
                .content("오늘 다일리 어떤가요~~")
                .pageImage("https://imageURL13.com")
                .writerId(4L)
                .writerNickname("다솔맘")
                .hashtags(List.of("육아"))
                .createdTime(POST_CREATED_TIME)
                .hotPostCreatedTime(HOT_POST_CREATED_TIME)
                .likeCount(8L)
                .build();

        HotPostReadResponseDTO 인기게시글3 = HotPostReadResponseDTO.builder()
                .postId(38L)
                .content("오늘 업무량 실화냐..")
                .pageImage("https://imageURL123.com")
                .writerId(31L)
                .writerNickname("다일리개발자")
                .hashtags(List.of("개발자", "회사", "퇴사"))
                .createdTime(POST_CREATED_TIME)
                .hotPostCreatedTime(HOT_POST_CREATED_TIME)
                .likeCount(33L)
                .build();

        HotPostReadResponseDTO 인기게시글4 = HotPostReadResponseDTO.builder()
                .postId(39L)
                .content("주말 다일리 어떤가요?")
                .pageImage("https://imageURL1413.com")
                .writerId(83L)
                .writerNickname("주말좋아")
                .hashtags(List.of("주말", "휴식"))
                .createdTime(POST_CREATED_TIME)
                .hotPostCreatedTime(HOT_POST_CREATED_TIME)
                .likeCount(23L)
                .build();

        HotPostReadResponseDTO 인기게시글5 = HotPostReadResponseDTO.builder()
                .postId(40L)
                .content("유럽여행 25일차 일정 짜봤습니다.")
                .pageImage("https://imageURL23123.com")
                .writerId(512L)
                .writerNickname("여행유튜버")
                .hashtags(List.of("여행", "유럽여행"))
                .createdTime(POST_CREATED_TIME)
                .hotPostCreatedTime(HOT_POST_CREATED_TIME)
                .likeCount(212L)
                .build();

        return HotPostReadSliceResponseDTO
                .builder()
                .hasNext(true)
                .presentPage(7)
                .hotPosts(List.of(인기게시글1, 인기게시글2, 인기게시글3, 인기게시글4, 인기게시글5))
                .build();
    }
}
