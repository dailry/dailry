package com.daily.daily.postcomment.dto;

import com.daily.daily.postcomment.domain.PostComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostCommentResponseDTO {
    private Long commentId;
    private Long postId;
    private Long writerId;
    private String writerNickname;
    private String content;
    private LocalDateTime createdTime;

    public static PostCommentResponseDTO from(PostComment postComment) {
        return PostCommentResponseDTO.builder()
                .commentId(postComment.getId())
                .postId(postComment.getPostId())
                .writerId(postComment.getWriterId())
                .writerNickname(postComment.getWriterNickname())
                .content(postComment.getContent())
                .createdTime(postComment.getCreatedTime())
                .build();
    }
}
