package com.daily.daily.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class HotHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagName;
    private Long postCount;

    protected HotHashtag() {
    }

    public static HotHashtag of(String tagName, Long postCount) {
        return new HotHashtag(tagName, postCount);
    }
    private HotHashtag(String tagName, Long postCount) {
        this.tagName = tagName;
        this.postCount = postCount;
    }
}
