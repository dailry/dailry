package com.daily.daily.postcomment.repository;

import com.daily.daily.post.domain.Post;
import com.daily.daily.post.repository.PostRepository;
import com.daily.daily.postcomment.domain.PostComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class PostCommentRepositoryTest {

    @Autowired PostCommentRepository commentRepository;

    @Autowired PostRepository postRepository;
    @Test
    void findAllByPostId() {
        Post post = Post.builder().build();
        postRepository.save(post);
        for (int i = 0; i < 21; i++) {
            PostComment postComment = PostComment.builder().post(post).content("ㅇㅅㅇ" ).build();
            PostComment save = commentRepository.save(postComment);
            System.out.println(save.getPostId());
            System.out.println(save.getId());
            System.out.println("--");
        }

        PageRequest pageRequest = PageRequest.of(5, 30);

        Page<PostComment> comments2 = commentRepository.findByPostId(1L, pageRequest);
        System.out.println(comments2.getTotalElements());
        System.out.println("size : " + comments2.getContent().size());
    }
}