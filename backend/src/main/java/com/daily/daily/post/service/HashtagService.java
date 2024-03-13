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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagService {
    private static final String DEFAULT_HASHTAG = "일반";

    private final HashtagRepository hashtagRepository;

    private final HotHashtagRepository hotHashtagRepository;

    private List<HotHashtag> hotHashtags;

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
        /**
         * 현재 코드는 기존 post가 가지고 있는 해시태그의 갯수만큼 delete 쿼리가 발생한다.
         * 쿼리를 최소화 하고싶으면, 게시글에 있는 해시태그가 수정목록에 존재하지 않으면 목록에서 지우고, 꼭 추가해야만 하는 해시태그만 add 해주면 된다.
         * 대신 이러면 코드가 15줄은 더 늘어난다.ㅠ ㅠ
         */


        post.clearHashtags();
        addHashtagsToPost(post, hashtags);
    }

    private Hashtag createHashtag(String tagName) {
        return hashtagRepository.save(Hashtag.of(tagName));
    }

    @Scheduled(cron = "0 0 * * * *") // 매 시 정각마다 실행
    public void findAndUpdateHotHashTags() {
        hotHashtags = hotHashtagRepository.findHotHashTags();
        hotHashtagRepository.saveAll(hotHashtags);
    }

    public HotHashtagReadListResponseDTO findHotHashTags() {
        return HotHashtagReadListResponseDTO.from(hotHashtags);
    }
}
