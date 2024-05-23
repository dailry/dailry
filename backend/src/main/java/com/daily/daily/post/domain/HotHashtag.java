package com.daily.daily.post.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class HotHashtag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postCount;
    @OneToOne(fetch = FetchType.LAZY)
    private Hashtag hashtag;

    protected HotHashtag() {
    }

    public static HotHashtag of(Long postCount, Hashtag hashtag) {
        return new HotHashtag(postCount, hashtag);
    }
    private HotHashtag(Long postCount, Hashtag hashtag) {
        this.hashtag = hashtag;
        this.postCount = postCount;
    }
}
