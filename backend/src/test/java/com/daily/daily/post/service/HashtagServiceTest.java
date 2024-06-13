package com.daily.daily.post.service;

import com.daily.daily.post.domain.Hashtag;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostHashtag;
import com.daily.daily.post.repository.HashtagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HashtagServiceTest {

    @Mock
    HashtagRepository hashtagRepository;
    @InjectMocks
    HashtagService hashtagService;

    @Nested
    @DisplayName("addHashtagsToPost() - 게시글에 해시태그 추가 메서드 테스트")
    class addHashtagsToPost {

        @Test
        @DisplayName("해시태그 목록들이 비어있으면, DEFAULT_HASHTAGS 인 '일반' 해시태그를 붙여줘야 한다. ")
        void test1() {
            //given
            List<String> emptyHashtags = List.of("");
            Post post = Post.builder().build();

            when(hashtagRepository.findByTagName("일반")).thenReturn(Optional.of(Hashtag.of("일반")));

            //when
            hashtagService.addHashtagsToPost(post, emptyHashtags);

            //then
            assertThat(post.getPostHashtags().size()).isEqualTo(1);
            assertThat(post.getPostHashtags().get(0).getTagName()).isEqualTo("일반");
        }

        @Test
        @DisplayName("인자에 들어오는 해시태그 목록에 맞춰서, 게시글에 해시태그들이 추가되어야 한다.")
        void test2() {
            //given
            List<String> hashtags = List.of("일상", "점심", "학식");
            Post post = Post.builder().build();

            when(hashtagRepository.findByTagName("일상")).thenReturn(Optional.of(Hashtag.of("일상")));
            when(hashtagRepository.findByTagName("점심")).thenReturn(Optional.of(Hashtag.of("점심")));
            when(hashtagRepository.findByTagName("학식")).thenReturn(Optional.of(Hashtag.of("학식")));

            //when
            hashtagService.addHashtagsToPost(post, hashtags);

            //then
            assertThat(post.getPostHashtags().size()).isEqualTo(3);

            List<String> resultHashtags = post.getPostHashtags().stream()
                    .map(PostHashtag::getTagName)
                    .collect(Collectors.toList());

            assertThat(resultHashtags).isEqualTo(hashtags);
        }

        @Test
        @DisplayName("인자로 들어오는 해시태그가 DB에 등록되지 않은 해시태그라면 HashtagRepository.save()를 호출해서 추가해야 한다.")
        void test3() {
            //given
            List<String> hashtags = List.of("일상", "점심", "학식");
            Post post = Post.builder().build();

            when(hashtagRepository.findByTagName(any())).thenReturn(Optional.empty());

            //when
            hashtagService.addHashtagsToPost(post, hashtags);

            //then
            verify(hashtagRepository, times(3)).save(any());
        }
    }

    @Nested
    @DisplayName("updateHashtagsInPost() - 게시글에 있는 해시태그 수정 메서드 테스트")
    class updateHashtagsInPost {
        @Test
        @DisplayName("인자로 들어오는 해시태그 목록이 비어있으면, DEFAULT_HASHTAGS 인 '일반' 해시태그를 붙여줘야 한다.")
        void test1() {
            //given
            List<String> emptyHashtags = List.of("");
            Post post = Post.builder().build();

            when(hashtagRepository.findByTagName("일반")).thenReturn(Optional.of(Hashtag.of("일반")));

            //when
            hashtagService.updateHashtagsInPost(post, emptyHashtags);

            //then
            assertThat(post.getPostHashtags().size()).isEqualTo(1);
            assertThat(post.getPostHashtags().get(0).getTagName()).isEqualTo("일반");
        }

        @Test
        @DisplayName("인자로 들어오는 해시태그 목록과 일치하게 Post에 해시태그들이 추가되어야만 한다.")
        void test2() {
            //given
            List<String> hashtags = List.of("시험기간", "대학생");
            Post post = Post.builder().build();

            when(hashtagRepository.findByTagName("시험기간")).thenReturn(Optional.of(Hashtag.of("시험기간")));
            when(hashtagRepository.findByTagName("대학생")).thenReturn(Optional.of(Hashtag.of("대학생")));

            //when
            hashtagService.updateHashtagsInPost(post, hashtags);

            //then
            assertThat(post.getPostHashtags().size()).isEqualTo(2);

            List<String> resultHashtags = post.getPostHashtags().stream()
                    .map(PostHashtag::getTagName)
                    .collect(Collectors.toList());

            assertThat(resultHashtags).isEqualTo(hashtags);
        }
    }
}