package com.daily.daily.postcomment.fixture;

import com.daily.daily.member.domain.Member;
import com.daily.daily.postcomment.domain.PostComment;
import com.daily.daily.postcomment.dto.PostCommentRequestDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;

import java.time.LocalDateTime;

import static com.daily.daily.member.fixture.MemberFixture.일반회원2;
import static com.daily.daily.post.fixture.PostFixture.POST_ID;
import static com.daily.daily.post.fixture.PostFixture.일반회원1이_작성한_게시글;

public class PostCommentFixture {

    private static final Long COMMENT_ID = 16L;
    private static final String 댓글_내용 = "오오.. 오늘 계획 알차시네요!!";
    private static final String 수정_댓글_내용 = "댓글 수정하기 ㅇㅅㅇ";
    private static final LocalDateTime 댓글_생성_시간 = LocalDateTime.of(2024, 1, 20, 6, 52, 30, 4);

    public static PostCommentRequestDTO 댓글_생성_DTO() {
        return new PostCommentRequestDTO(댓글_내용);
    }

    public static PostCommentRequestDTO 댓글_수정_DTO() {
        return new PostCommentRequestDTO(수정_댓글_내용);
    }

    public static PostCommentResponseDTO 댓글_응답_DTO() {
        Member 일반회원2 = 일반회원2();

        return PostCommentResponseDTO.builder()
                .commentId(COMMENT_ID)
                .postId(POST_ID)
                .writerId(일반회원2.getId())
                .writerNickname(일반회원2.getNickname())
                .createdTime(댓글_생성_시간)
                .build();
    }
    
    public static PostComment 일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글() {
        return PostComment.builder()
                .id(COMMENT_ID)
                .content(댓글_내용)
                .post(일반회원1이_작성한_게시글())
                .commentWriter(일반회원2())
                .build();

    }

    
}
