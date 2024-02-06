package com.daily.daily.post.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
public class Hashtag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagName;

    protected Hashtag() {
    }

    public static Hashtag of(String tagName) {
        return new Hashtag(tagName);
    }
    private Hashtag (String tagName) {
        this.tagName = tagName;
    }
}
