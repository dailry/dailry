package com.daily.daily.post.dto;

import com.daily.daily.member.domain.Member;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostHashtag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode
public class PostWriteResponseDTO { // 수정, 삭제 전용 응답 DTO
    private Long postId;
    private String content;
    private String pageImage;
    private Set<String> hashtags;
    private Long writerId;
    private String writerNickname;
    private LocalDateTime createdTime;

    public static PostWriteResponseDTO from(Post post) {
        Member postWriter = post.getPostWriter();

        return PostWriteResponseDTO.builder()
                .postId(post.getId())
                .content(post.getContent())
                .pageImage(post.getPageImage())
                .hashtags(post.getTagNames())
                .writerId(postWriter.getId())
                .writerNickname(postWriter.getNickname())
                .createdTime(post.getCreatedTime())
                .build();
    }
}
