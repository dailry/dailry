package com.daily.daily.postcomment.repository;

import com.daily.daily.common.config.QuerydslConfig;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.repository.PostRepository;
import com.daily.daily.postcomment.domain.PostComment;
import com.daily.daily.testutil.config.JpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostCommentRepositoryTest extends JpaTest {

    @Autowired PostCommentRepository commentRepository;

    @Autowired PostRepository postRepository;
    @Test
    @DisplayName("PageRequest에 맞게 슬라이스 객체를 반환하는지 확인한다.")
    void findAllByPostId() {
        Post post = postRepository.save(Post.builder().build());

        //댓글 저장 (20개 저장)
        for (int i = 1; i <=20; i++) {
            PostComment postComment = PostComment.builder()
                    .post(post)
                    .content(i + "번째")
                    .build();

            commentRepository.save(postComment);
        }

        int pageNumber = 3;
        int pageSize = 5;
        Slice<PostComment> comments = commentRepository.findByPostId(post.getId(), PageRequest.of(pageNumber, pageSize));

        assertThat(comments.getSize()).isEqualTo(pageSize);
        assertThat(comments.getNumber()).isEqualTo(pageNumber);
        assertThat(comments.hasNext()).isFalse(); //총 댓글이 20개이므로 (pageNumber + 1) * pageSize = 20. 따라서 hasNext()는 false 이다.

        List<PostComment> content = comments.getContent();
        assertThat(content.get(1).getPostId()).isEqualTo(post.getId());
    }
}