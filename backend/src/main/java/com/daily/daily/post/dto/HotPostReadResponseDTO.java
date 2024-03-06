package com.daily.daily.post.dto;

import com.daily.daily.post.domain.HotPost;
import com.daily.daily.post.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class HotPostReadResponseDTO{
    //기존 PostReadDTO
    private Long postId;
    private String content;
    private String pageImage;
    private Long writerId;
    private String writerNickname;
    private List<String> hashtags;
    private Long likeCount;
    private LocalDateTime createdTime;

    //추가된 항목
    private LocalDateTime hotPostCreatedTime;
    public static HotPostReadResponseDTO from(HotPost hotPost) {
        Post post = hotPost.getPost();

        return HotPostReadResponseDTO.builder()
                .postId(post.getId())
                .content(post.getContent())
                .pageImage(post.getPageImage())
                .writerId(post.getWriterId())
                .writerNickname(post.getWriterNickname())
                .hashtags(post.getTagNames())
                .createdTime(post.getCreatedTime())
                .likeCount(post.getLikeCount())
                .hotPostCreatedTime(hotPost.getCreatedTime())
                .build();
    }
}
