package com.daily.daily.post.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import com.daily.daily.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.daily.daily.common.config.BusinessConfig.HOT_POST_LIKE_THRESHOLD;
import static com.daily.daily.common.config.BusinessConfig.HOT_POST_CREATED_TIME_CONDITION;


@Entity
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String pageImage; // 이미지 파일 경로 저장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member postWriter;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PostHashtag> postHashtags = new ArrayList<>();

    private long likeCount;
    private boolean isHotPost = false;
    @Version
    private long version;

    @Builder
    public Post(String content, String pageImage, Member postWriter) {
        this.content = content;
        this.pageImage = pageImage;
        this.postWriter = postWriter;
    }

    protected Post() {
    }

    public void updatePageImage(String pageImage) {
        this.pageImage = pageImage;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void addPostHashtag(PostHashtag postHashtag) {
        postHashtags.add(postHashtag);
        postHashtag.setPost(this);  // 연관관계 편의 로직.
    }

    public void clearHashtags() {
        postHashtags.clear();
    }

    public boolean isWrittenBy(Long writerId) {
        return writerId.equals(this.getWriterId());
    }

    public List<String> getTagNames() {
        return postHashtags.stream()
                .map(PostHashtag::getTagName)
                .collect(Collectors.toList());
    }

    public Long getWriterId() {
        return postWriter.getId();
    }

    public String getWriterNickname() {
        return postWriter.getNickname();
    }

    public void increaseLikeCount() {
        likeCount++;
    }

    public void decreaseLikeCount() {
        likeCount--;
    }

    public boolean satisfyHotPostCondition() {
        return likeCount >= HOT_POST_LIKE_THRESHOLD && getCreatedTime().isAfter(LocalDateTime.now().minusDays(HOT_POST_CREATED_TIME_CONDITION));
    }

    public void makeHotPost() {
        isHotPost = true;
    }
}
