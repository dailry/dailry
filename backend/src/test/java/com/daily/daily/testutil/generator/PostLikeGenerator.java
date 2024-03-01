package com.daily.daily.testutil.generator;

import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostLike;
import com.daily.daily.post.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostLikeGenerator {

    @Autowired
    PostLikeRepository postLikeRepository;

    public List<PostLike> generate(Post post, int count) {
        List<PostLike> postLikes = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            PostLike like = PostLike.builder().post(post).build();
            postLikes.add(postLikeRepository.save(like));
        }

        return postLikes;
    }

}
