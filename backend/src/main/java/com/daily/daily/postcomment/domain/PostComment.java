package com.daily.daily.postcomment.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import com.daily.daily.member.domain.Member;
import com.daily.daily.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
public class PostComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member commentWriter;

    @Builder
    public PostComment(String content, Post post, Member commentWriter) {
        this.content = content;
        this.post = post;
        this.commentWriter = commentWriter;
    }

    protected PostComment() {
    }

    public Long getPostId() {
        return post.getId();
    }

    public Long getWriterId() {
        return commentWriter.getId();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public String getWriterNickname() {
        return commentWriter.getNickname();
    }

    public boolean isWrittenBy(Long memberId) {
        return getWriterId().longValue() == memberId.longValue();
    }
}
