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
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    private Long likeCount;

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

}
