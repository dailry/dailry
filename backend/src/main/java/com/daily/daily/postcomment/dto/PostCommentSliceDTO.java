package com.daily.daily.postcomment.dto;

import com.daily.daily.postcomment.domain.PostComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostCommentSliceDTO {
    private Integer presentPage;
    private boolean hasNext;
    private List<SingleCommentDTO> comments;

    public static PostCommentSliceDTO from(Slice<PostComment> commentSlice) {
        List<SingleCommentDTO> commentsDTO = convertToDTO(commentSlice.getContent());

        return builder()
                .presentPage(commentSlice.getNumber())
                .hasNext(commentSlice.hasNext())
                .comments(commentsDTO)
                .build();
    }

    private static List<SingleCommentDTO> convertToDTO(List<PostComment> comments) {
        return comments.stream()
                .map(comment -> SingleCommentDTO.builder()
                        .commentId(comment.getId())
                        .content(comment.getContent())
                        .writerNickname(comment.getWriterNickname())
                        .writerId(comment.getWriterId())
                        .createdTime(comment.getCreatedTime())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Setter
    public static class SingleCommentDTO {
        private Long commentId;
        private String content;
        private String writerNickname;
        private Long writerId;
        private LocalDateTime createdTime;
    }
}
