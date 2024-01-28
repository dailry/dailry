package com.daily.daily.post.service;

import com.daily.daily.post.domain.Hashtag;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostHashtag;
import com.daily.daily.post.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagService {
    private static final String DEFAULT_HASHTAG = "일반";

    private final HashtagRepository hashtagRepository;

    public void addHashtagsToPost(Post post, Set<String> hashtags) {
        Set<Hashtag> hashtagEntities = findHashtagsOrCreate(hashtags);
        hashtagEntities.forEach(hashtagEntity -> post.addPostHashtag(PostHashtag.of(post, hashtagEntity)));
    }

    private Set<Hashtag> findHashtagsOrCreate(Set<String> hashtags) {
        hashtags = getDefaultHashtagsIfEmpty(hashtags);

        return hashtags.stream()
                .map(tagName -> hashtagRepository.findByTagName(tagName).orElseGet(() -> createHashtag(tagName)))
                .collect(Collectors.toSet());
    }

    private Set<String> getDefaultHashtagsIfEmpty(Set<String> hashtags) {
        if (hashtags == null || hashtags.isEmpty() || hashtags.stream().allMatch(String::isBlank)) {
            hashtags = Set.of(DEFAULT_HASHTAG);
        }
        return hashtags;
    }

    public void updateHashtagsInPost(Post post, Set<String> hashtags) {
        post.clearHashtags();
        addHashtagsToPost(post, hashtags);
    }

    private Hashtag createHashtag(String tagName) {
        return hashtagRepository.save(Hashtag.of(tagName));
    }
}
