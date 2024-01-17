package com.daily.daily.post.service;

import com.daily.daily.common.service.S3StorageService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.dto.PostCreateDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import com.daily.daily.post.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    @DisplayName("게시글을 생성하는 create() 메서드가 실행되었을 때, 이미지 파일을 업로드하는 메서드와, " +
            "게시글을 저장하는 메서드는 반드시 한 번 이상 호출되어야 한다. + 반환되는 ResponseDTO 결과값이 올바른지 검사한다.")
    void create() {
        MultipartFile pageImage = new MockMultipartFile("file", "".getBytes());

        Member findMember = Member.builder()
                .id(3L)
                .nickname("졸린너구리")
                .build();

        Post savedPost = Post.builder()
                .id(3L)
                .content("제 페이지좀 봐주세요")
                .pageImage("imagePath")
                .member(findMember)
                .build();

        when(memberRepository.findById(any())).thenReturn(Optional.of(findMember));
        when(storageService.uploadImage(any(), any())).thenReturn("imagePath");
        when(postRepository.save(any())).thenReturn(savedPost);

        //when
        PostResponseDTO result = postService.create(3L, new PostCreateDTO(), pageImage);

        //then
        verify(storageService, times(1)).uploadImage(any(), any());
        verify(postRepository, times(1)).save(any());

        assertThat(result.getWriter()).isEqualTo("졸린너구리");
        assertThat(result.getContent()).isEqualTo("제 페이지좀 봐주세요");
        assertThat(result.getPageImage()).isEqualTo("imagePath");
        assertThat(result.getMemberId()).isEqualTo(3L);
    }
}