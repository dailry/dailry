package com.daily.daily.postcomment.repository;

import com.daily.daily.post.domain.Post;
import com.daily.daily.post.repository.PostRepository;
import com.daily.daily.postcomment.domain.PostComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostCommentRepositoryTest {

    @Autowired PostCommentRepository commentRepository;

    @Autowired PostRepository postRepository;
    @Test
    @DisplayName("PageRequest에 맞게 슬라이스 객체를 반환하는지 확인한다.")
    void findAllByPostId() {
        postRepository.save(Post.builder().build());
        postRepository.save(Post.builder().build());
        Post savedPost = postRepository.save(Post.builder().build()); // 저장된 postId는 3이다.


        //댓글 저장 (20개 저장)
        for (int i = 1; i <=20; i++) {
            PostComment postComment = PostComment.builder()
                    .post(savedPost)
                    .content(i + "번째" )
                    .build();

            commentRepository.save(postComment);
        }

        int pageNumber = 3;
        int pageSize = 5;
        Slice<PostComment> comments = commentRepository.findByPostId(3L, PageRequest.of(pageNumber, pageSize));

        assertThat(comments.getSize()).isEqualTo(pageSize);
        assertThat(comments.getNumber()).isEqualTo(pageNumber);
        assertThat(comments.hasNext()).isFalse();
        assertThat(comments.getContent().get(1).getPostId()).isEqualTo(3L);
    }
}