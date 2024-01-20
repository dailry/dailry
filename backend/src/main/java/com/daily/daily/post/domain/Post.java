package com.daily.daily.post.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import com.daily.daily.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String pageImage; // 이미지 파일 경로 저장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Member postWriter;

//    @ManyToOne
//    private HashTags hashTags


    public void updatePageImage(String pageImage) {
        this.pageImage = pageImage;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
