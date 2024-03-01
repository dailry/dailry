package com.daily.daily.testutil.generator;

import com.daily.daily.post.domain.Hashtag;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostHashtag;
import com.daily.daily.post.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HashtagGenerator {

    @Autowired
    HashtagRepository hashtagRepository;
    public List<PostHashtag> generate(Post post, String... hashtags) {
        List<PostHashtag> postHashtags = new ArrayList<>();

        for (String hashtag : hashtags) {
            Hashtag hashtagEntity = Hashtag.of(hashtag);
            hashtagRepository.save(hashtagEntity);

            PostHashtag postHashtag = PostHashtag.of(post, hashtagEntity);

            post.addPostHashtag(postHashtag);
            postHashtags.add(postHashtag);
        }

        return postHashtags;
    }
}
