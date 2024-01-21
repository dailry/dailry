package com.daily.daily.postcomment.service;

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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.daily.daily.member.fixture.MemberFixture.일반회원2;
import static com.daily.daily.member.fixture.MemberFixture.일반회원2_ID;
import static com.daily.daily.post.fixture.PostFixture.POST_ID;
import static com.daily.daily.post.fixture.PostFixture.일반회원1이_작성한_게시글;
import static com.daily.daily.postcomment.fixture.PostCommentFixture.댓글_요청_DTO;
import static org.assertj.core.api.Assertions.assertThat;
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
    PostCommentRepository postCommentRepository;
    @InjectMocks
    PostCommentService postCommentService;

    @Nested
    @DisplayName("create() - 댓글 생성 메서드 테스트")
    class create {
        @Test
        @DisplayName("댓글을 저장하는 메서드는 한 번 실행되어야 한다. 그리고 반환 결과는 파라미터로 들어간 값들이 포함되어야한다.")
        void test1() {
            //given
            Member 댓글작성자 = 일반회원2();
            Post 게시글 = 일반회원1이_작성한_게시글();
            PostCommentRequestDTO 댓글_요청_DTO = 댓글_요청_DTO();

            //when
            when(memberRepository.findById(any())).thenReturn(Optional.of(댓글작성자));
            when(postRepository.findById(any())).thenReturn(Optional.of(게시글));

            PostCommentResponseDTO 결과 = postCommentService.create(댓글작성자.getId(), 게시글.getId(), 댓글_요청_DTO);

            //then
            verify(postCommentRepository, times(1)).save(any());
            assertThat(결과.getWriterId()).isEqualTo(댓글작성자.getId());
            assertThat(결과.getPostId()).isEqualTo(게시글.getId());
            assertThat(결과.getContent()).isEqualTo(댓글_요청_DTO.getContent());
        }
    }
}