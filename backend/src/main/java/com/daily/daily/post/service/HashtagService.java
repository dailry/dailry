package com.daily.daily.post.service;

import com.daily.daily.post.domain.Hashtag;
import com.daily.daily.post.domain.HotHashtag;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostHashtag;
import com.daily.daily.post.dto.HotHashtagReadListResponseDTO;
import com.daily.daily.post.repository.HashtagRepository;
import com.daily.daily.post.repository.HotHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagService {
    private static final String DEFAULT_HASHTAG = "일반";

    private final HashtagRepository hashtagRepository;

    private final HotHashtagRepository hotHashtagRepository;
    private List<HotHashtag> hotHashtags;

    public void addHashtagsToPost(Post post, List<String> hashtags) {
        hashtags = hashtags.stream().distinct().toList();

        List<Hashtag> hashtagEntities = findHashtagsOrCreate(hashtags);
        hashtagEntities.forEach(hashtagEntity -> post.addPostHashtag(PostHashtag.of(post, hashtagEntity)));
    }

    private List<Hashtag> findHashtagsOrCreate(List<String> hashtags) {
        hashtags = getDefaultHashtagsIfEmpty(hashtags);

        return hashtags.stream()
                .map(tagName -> hashtagRepository.findByTagName(tagName).orElseGet(() -> createHashtag(tagName)))
                .collect(Collectors.toList());
    }

    private List<String> getDefaultHashtagsIfEmpty(List<String> hashtags) {
        if (hashtags == null || hashtags.isEmpty() || hashtags.stream().allMatch(String::isBlank)) {
            hashtags = List.of(DEFAULT_HASHTAG);
        }
        return hashtags;
    }

    public void updateHashtagsInPost(Post post, List<String> hashtags) {
        post.clearHashtags();
        addHashtagsToPost(post, hashtags);
    }

    private Hashtag createHashtag(String tagName) {
        return hashtagRepository.save(Hashtag.of(tagName));
    }

    @Scheduled(cron = "0 0 * * * *") // 매 시 정각마다 실행
    public void findAndUpdateHotHashTags() {
        List<HotHashtag> findHotHashtags = hotHashtagRepository.findHotHashTags();
        if (findHotHashtags.isEmpty()) {
            return;
        }

        this.hotHashtags = findHotHashtags;
        hotHashtagRepository.saveAll(hotHashtags);
    }

    public HotHashtagReadListResponseDTO findHotHashTags() {
        return HotHashtagReadListResponseDTO.from(hotHashtags);
    }
}
