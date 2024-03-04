package com.daily.daily.testutil.generator;

import com.daily.daily.post.domain.Post;
import com.daily.daily.postcomment.domain.PostComment;
import com.daily.daily.postcomment.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostCommentGenerator {

    @Autowired
    PostCommentRepository postCommentRepository;
    public List<PostComment> generate(Post post, int count) {
        List<PostComment> postComments = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            PostComment postComment = PostComment.builder().post(post).build();
            postComments.add(postCommentRepository.save(postComment));
        }

        return postComments;
    }
}
