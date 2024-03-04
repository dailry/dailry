package com.daily.daily.post.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostLike;
import com.daily.daily.post.exception.AlreadyLikeException;
import com.daily.daily.post.exception.NotPreviouslyLikedException;
import com.daily.daily.post.repository.PostLikeRepository;
import com.daily.daily.post.repository.PostRepository;
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
import static com.daily.daily.post.fixture.PostFixture.일반회원1이_작성한_게시글;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest {

    @Mock
    PostLikeRepository likeRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PostRepository postRepository;
    @InjectMocks
    PostLikeService postLikeService;

    @Nested
    @DisplayName("increaseLikeCount() - 좋아요 증가 메서드 테스트")
    class increaseLikeCount{
        @Test
        @DisplayName("memberId와 postId로 좋아요 이력을 조회 했을 때 이미 존재하면 AlreadyLikeException 예외를 발생한다")
        void test1() {
            //given
            PostLike like = PostLike
                    .builder()
                    .build();

            when(likeRepository.findBy(any(Long.class), any(Long.class))).thenReturn(Optional.of(like));

            //when
            assertThatThrownBy(() -> postLikeService.increaseLikeCount(any(Long.class), any(Long.class)))
                    .isInstanceOf(AlreadyLikeException.class);
        }

        @Test
        @DisplayName("좋아요 이력을 저장할 때, 파라미터 정보에 맞게 올바르게 저장하는지 검사한다.")
        void test2() {
            //given : 일반회원 2가 일반회원 1이작성한 게시글에 좋아요를 누른다.
            Member 일반회원2 = 일반회원2();
            Post 일반회원1이_작성한_게시글 = 일반회원1이_작성한_게시글();

            when(memberRepository.findById(일반회원2.getId())).thenReturn(Optional.of(일반회원2()));
            when(postRepository.findById(일반회원1이_작성한_게시글.getId())).thenReturn(Optional.of(일반회원1이_작성한_게시글));

            ArgumentCaptor<PostLike> postLikeCaptor = ArgumentCaptor.forClass(PostLike.class);

            //when
            postLikeService.increaseLikeCount(일반회원2.getId(), 일반회원1이_작성한_게시글.getId());

            //then
            verify(likeRepository, times(1)).save(postLikeCaptor.capture());

            PostLike savedPostLike = postLikeCaptor.getValue();

            assertThat(savedPostLike.getMember().getId()).isEqualTo(일반회원2.getId());
            assertThat(savedPostLike.getPost().getId()).isEqualTo(일반회원1이_작성한_게시글.getId());
        }
    }


    @Nested
    @DisplayName("decreaseLikeCount() - 좋아요 감소 메서드 테스트")
    class decreaseLikeCount{
        @Test
        @DisplayName("memberId와 postId로 좋아요 이력을 조회 했을 때, 좋아요를 누른 이력이 없는데, 좋아요를 감소시키려고 하는 경우 LikeDecreaseNotAllowedException 예외를 발생한다")
        void test1() {
            //given
            when(likeRepository.findBy(any(Long.class), any(Long.class))).thenReturn(Optional.empty());

            //when
            assertThatThrownBy(() -> postLikeService.decreaseLikeCount(any(Long.class), any(Long.class)))
                    .isInstanceOf(NotPreviouslyLikedException.class);
        }
    }

}