package com.daily.daily.postcomment.fixture;

import com.daily.daily.postcomment.domain.PostComment;
import com.daily.daily.postcomment.dto.PostCommentSliceDTO;
import com.daily.daily.postcomment.dto.PostCommentRequestDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.daily.daily.member.fixture.MemberFixture.일반회원2;
import static com.daily.daily.post.fixture.PostFixture.일반회원1이_작성한_게시글;

public class PostCommentFixture {

    public static final Long COMMENT_ID = 16L;
    private static final String 댓글_내용 = "오오.. 오늘 계획 알차시네요!!";
    private static final String 수정_댓글_내용 = "댓글 수정하기 ㅁㄴㅇㄹ";
    private static final LocalDateTime 댓글_생성_시간 = LocalDateTime.of(2024, 1, 20, 6, 52, 30, 4);
    public static final int 요청_페이지_숫자 = 0;
    public static final int 요청_페이지_사이즈 = 3;

    public static PostCommentRequestDTO 댓글_생성_DTO() {
        return new PostCommentRequestDTO(댓글_내용);
    }

    public static PostCommentRequestDTO 댓글_수정_DTO() {
        return new PostCommentRequestDTO(수정_댓글_내용);
    }

    public static PostCommentResponseDTO 댓글_응답_DTO() {
        PostCommentResponseDTO 댓글_응답_DTO = PostCommentResponseDTO.builder()
                .commentId(COMMENT_ID)
                .postId(4L)
                .writerId(10L)
                .writerNickname("gomudayya")
                .content("오오 멋있어요")
                .createdTime(댓글_생성_시간)
                .build();

        return 댓글_응답_DTO;
    }

    public static PostCommentResponseDTO 수정된_댓글_응답_DTO() {
        PostCommentResponseDTO result = 댓글_응답_DTO();
        result.setContent(수정_댓글_내용);

        return result;
    }

    public static PostCommentSliceDTO 댓글_조회_페이징_DTO() {
        PostCommentSliceDTO.SingleCommentDTO 첫번째_댓글 = PostCommentSliceDTO.SingleCommentDTO.builder()
                .commentId(2L)
                .createdTime(댓글_생성_시간)
                .content("와우")
                .writerNickname("뚱냥이")
                .writerId(1L)
                .build();

        PostCommentSliceDTO.SingleCommentDTO 두번째_댓글 = PostCommentSliceDTO.SingleCommentDTO.builder()
                .commentId(5L)
                .createdTime(댓글_생성_시간)
                .content("허걱")
                .writerNickname("돼냥이")
                .writerId(2L)
                .build();

        PostCommentSliceDTO.SingleCommentDTO 세번째_댓글 = PostCommentSliceDTO.SingleCommentDTO.builder()
                .commentId(16L)
                .createdTime(댓글_생성_시간)
                .content("오오 멋있어요")
                .writerNickname("gomudayya")
                .writerId(3L)
                .build();

        PostCommentSliceDTO 댓글_조회_페이징_DTO = PostCommentSliceDTO.builder()
                .hasNext(true)
                .presentPage(요청_페이지_숫자)
                .comments(List.of(첫번째_댓글, 두번째_댓글, 세번째_댓글))
                .build();

        return 댓글_조회_페이징_DTO;
    }

    public static PostCommentSliceDTO 댓글_memberID로_조회_페이징_DTO() {
        PostCommentSliceDTO.SingleCommentDTO 첫번째_댓글 = PostCommentSliceDTO.SingleCommentDTO.builder()
                .commentId(2L)
                .createdTime(댓글_생성_시간)
                .content("와우")
                .writerNickname("배고프다")
                .writerId(5L)
                .build();

        PostCommentSliceDTO.SingleCommentDTO 두번째_댓글 = PostCommentSliceDTO.SingleCommentDTO.builder()
                .commentId(5L)
                .createdTime(댓글_생성_시간)
                .content("허걱")
                .writerNickname("배고프다")
                .writerId(5L)
                .build();

        PostCommentSliceDTO.SingleCommentDTO 세번째_댓글 = PostCommentSliceDTO.SingleCommentDTO.builder()
                .commentId(16L)
                .createdTime(댓글_생성_시간)
                .content("오오 멋있어요")
                .writerNickname("배고프다")
                .writerId(5L)
                .build();

        PostCommentSliceDTO 댓글_조회_페이징_DTO = PostCommentSliceDTO.builder()
                .hasNext(true)
                .presentPage(요청_페이지_숫자)
                .comments(List.of(첫번째_댓글, 두번째_댓글, 세번째_댓글))
                .build();

        return 댓글_조회_페이징_DTO;
    }

    public static PostComment 일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글() {
        PostComment 댓글 = PostComment.builder()
                .content(댓글_내용)
                .post(일반회원1이_작성한_게시글())
                .commentWriter(일반회원2())
                .build();

        ReflectionTestUtils.setField(댓글, "id", COMMENT_ID);
        return 댓글;
    }

    public static Pageable 댓글_페이징_요청_객체() {
        return PageRequest.of(요청_페이지_숫자, 요청_페이지_사이즈);
    }

    public static Slice<PostComment> 댓글_페이징_결과() {
        List<PostComment> result = new ArrayList<>();

        long start = 요청_페이지_숫자 * 요청_페이지_사이즈 + 1;
        long end = start + 요청_페이지_사이즈;

        for (long i = start; i < end; i++) {
            result.add(일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글(i + 5 * i, i + "번째 댓글입니다."));
        }

        return new SliceImpl<>(result, 댓글_페이징_요청_객체(), true);
    }

    private static PostComment 일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글(Long id, String content) {
        return PostComment.builder()
//                .id(id)
                .content(content)
                .post(일반회원1이_작성한_게시글())
                .commentWriter(일반회원2())
                .build();
    }
}
