package com.daily.daily.postcomment.service;

import com.daily.daily.common.exception.UnauthorizedAccessException;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.repository.PostRepository;
import com.daily.daily.postcomment.domain.PostComment;
import com.daily.daily.postcomment.dto.PostCommentRequestDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import com.daily.daily.postcomment.repository.PostCommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.daily.daily.member.fixture.MemberFixture.구글소셜회원;
import static com.daily.daily.member.fixture.MemberFixture.일반회원2;
import static com.daily.daily.post.fixture.PostFixture.일반회원1이_작성한_게시글;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_수정_DTO;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_생성_DTO;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostCommentServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    PostCommentRepository commentRepository;
    @InjectMocks
    PostCommentService commentService;

    @Nested
    @DisplayName("create() - 댓글 생성 메서드 테스트")
    class create {
        @Test
        @DisplayName("댓글을 저장하는 메서드는 한 번 실행되어야 한다. 그리고 반환 결과는 파라미터로 들어간 값들이 포함되어야한다.")
        void test1() {
            //given
            Member 댓글작성자 = 일반회원2();
            Post 게시글 = 일반회원1이_작성한_게시글();
            PostCommentRequestDTO 댓글_요청_DTO = 댓글_생성_DTO();

            when(memberRepository.findById(any())).thenReturn(Optional.of(댓글작성자));
            when(postRepository.findById(any())).thenReturn(Optional.of(게시글));
            //when
            PostCommentResponseDTO 결과 = commentService.create(댓글작성자.getId(), 게시글.getId(), 댓글_요청_DTO);

            //then
            verify(commentRepository, times(1)).save(any());
            assertThat(결과.getWriterId()).isEqualTo(댓글작성자.getId());
            assertThat(결과.getPostId()).isEqualTo(게시글.getId());
            assertThat(결과.getContent()).isEqualTo(댓글_요청_DTO.getContent());
        }
    }


    @Nested
    @DisplayName("update() - 댓글 수정 메서드 테스트")
    class update {
        @Test
        @DisplayName("파라미터에 들어온 값들을 토대로 댓글을 수정해야 한다.")
        void test1() {
            //given
            Member 댓글_수정_요청자 = 일반회원2();
            PostComment 댓글 = 일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글();
            PostCommentRequestDTO 댓글_요청_DTO = 댓글_수정_DTO();

            when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(댓글));

            //when
            PostCommentResponseDTO 결과 = commentService.update(댓글_수정_요청자.getId(), 댓글.getId(), 댓글_요청_DTO);

            //then
            assertThat(결과.getCommentId()).isEqualTo(댓글.getId());
            assertThat(결과.getContent()).isEqualTo(댓글_요청_DTO.getContent());
            assertThat(결과.getWriterId()).isEqualTo(댓글_수정_요청자.getId());
        }

        @Test
        @DisplayName("댓글 수정을 요청하는 사람이 해당 댓글의 작성자가 아니라면 UnauthorizedAccessException() 예외가 발생한다.")
        void test2() {
            //given
            Member 댓글_수정_요청자 = 구글소셜회원();

            when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글()));

            //when, then
            assertThatThrownBy(() -> commentService.update(댓글_수정_요청자.getId(), 482L, 댓글_생성_DTO()))
                    .isInstanceOf(UnauthorizedAccessException.class);
        }
    }


    @Nested
    @DisplayName("delete() - 댓글 수정 메서드 테스트")
    class delete {
        @Test
        @DisplayName("댓글을 삭제하는 메서드는 반드시 한 번 호출되어야 한다.")
        void test1() {
            //given
            Member 댓글_수정_요청자 = 일반회원2();
            PostComment 댓글 = 일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글();

            when(commentRepository.findById(any())).thenReturn(Optional.of(댓글));

            //when
            commentService.delete(댓글_수정_요청자.getId(), 댓글.getId());

            //then
            verify(commentRepository, times(1)).delete(댓글);
        }

        @Test
        @DisplayName("댓글 삭제를 요청하는 사람이 해당 댓글의 작성자가 아니라면 UnauthorizedAccessException() 예외가 발생한다.")
        void test2() {
            //given
            Member 댓글_수정_요청자 = 구글소셜회원();

            when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(일반회원2가_작성한_댓글_to_일반회원1이_작성한_게시글()));

            //when, then
            assertThatThrownBy(() -> commentService.delete(댓글_수정_요청자.getId(), 482L))
                    .isInstanceOf(UnauthorizedAccessException.class);
        }
    }
}