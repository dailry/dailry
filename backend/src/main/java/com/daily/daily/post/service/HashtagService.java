package com.daily.daily.post.service;

import com.daily.daily.post.domain.Hashtag;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostHashtag;
import com.daily.daily.post.repository.HashtagRepository;
import com.daily.daily.post.repository.PostHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    private final PostHashtagRepository postHashtagRepository;

    public void addHashtagsToPost(Post post, Set<String> hashtags) {
        Set<Hashtag> hashtagEntities = findHashTagsOrSave(hashtags);
        hashtagEntities.forEach(hashtag -> postHashtagRepository.save(PostHashtag.of(post, hashtag)));
    }

    private Set<Hashtag> findHashTagsOrSave(Set<String> hashtags) {
        if (hashtags == null || hashtags.isEmpty()) {
            hashtags = Set.of("일반");
        }

        return hashtags.stream()
                .map(hashtag -> hashtagRepository.findByTagName(hashtag).orElse(hashtagRepository.save(Hashtag.of(hashtag))))
                .collect(Collectors.toSet());
    }
}
