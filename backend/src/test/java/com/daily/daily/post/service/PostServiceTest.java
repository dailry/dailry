package com.daily.daily.post.service;

import com.daily.daily.common.service.S3StorageService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.dto.PostRequestDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import com.daily.daily.post.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static com.daily.daily.member.fixture.MemberFixture.일반회원;
import static com.daily.daily.post.fixture.PostFixture.POST_ID;
import static com.daily.daily.post.fixture.PostFixture.다일리_페이지_이미지_파일;
import static com.daily.daily.post.fixture.PostFixture.일반회원이_작성한_게시글;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    S3StorageService storageService;
    @InjectMocks
    PostService postService;

    @Nested
    @DisplayName("create() - 게시글 생성 메서드 테스트")
    class create {
        @Test
        @DisplayName("이미지 파일을 업로드하는 메서드와, 게시글을 저장하는 메서드는 반드시 한 번 이상 호출되어야 한다.")
        void verifyTest() {
            //given
            when(memberRepository.findById(any())).thenReturn(Optional.of(일반회원()));
            when(storageService.uploadImage(any(), any())).thenReturn("");
            when(postRepository.save(any())).thenReturn(일반회원이_작성한_게시글());

            //when
            postService.create(2L, new PostRequestDTO(), 다일리_페이지_이미지_파일());

            //then
            verify(storageService, times(1)).uploadImage(any(), any());
            verify(postRepository, times(1)).save(any());
        }

    }

    @Nested
    @DisplayName("update() - 게시글 수정 테스트")
    class updatePost {

        @Test
        @DisplayName("게시글 수정 요청에 pageImage파일이 null이면 해당 포스트의 pageImage값은 그대로여야한다.")
        void test1() {
            //given
            when(postRepository.findById(POST_ID)).thenReturn(Optional.of(일반회원이_작성한_게시글()));

            //when
            PostResponseDTO updateResult = postService.update(POST_ID, new PostRequestDTO(), null);

            //then
            assertThat(updateResult.getPageImage()).isEqualTo(일반회원이_작성한_게시글().getPageImage());
        }

        @Test
        @DisplayName("게시글 수정 요청에 pageImage파일이 존재하면, 해당 포스트의 pageImage값은 수정되어야 한다.")
        void test2() {
            //given
            when(postRepository.findById(POST_ID)).thenReturn(Optional.of(일반회원이_작성한_게시글()));
            when(storageService.uploadImage(any(), any())).thenReturn("post/awef-1231-xcvsdf-12312");

            //when
            PostResponseDTO updateResult = postService.update(POST_ID, new PostRequestDTO(), 다일리_페이지_이미지_파일());

            //then
            assertThat(updateResult.getPageImage()).isEqualTo("post/awef-1231-xcvsdf-12312");
        }
    }
}