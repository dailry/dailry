package com.daily.daily.post.domain;


import com.daily.daily.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class HotPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Post post;

    protected HotPost() {
    }

    public static HotPost of(Post post) {
        return new HotPost(post);
    }
    private HotPost(Post post) {
        this.post = post;
    }
}
