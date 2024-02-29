package com.daily.daily.testutil.generator;

import com.daily.daily.post.domain.Post;
import com.daily.daily.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostGenerator {
    @Autowired
    PostRepository postRepository;

    public Post generate() {return postRepository.save(Post.builder().build());}
}
