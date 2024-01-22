package com.daily.daily.postcomment.dto;

import com.daily.daily.postcomment.domain.PostComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostCommentPagingDTO {
    private int totalPage;
    private long totalCount;
    private int presentPage;
    private List<SingleCommentDTO> comments = new ArrayList<>();

    public static PostCommentPagingDTO from(Page<PostComment> commentPage) {
        List<SingleCommentDTO> commentsDTO = convertToDTO(commentPage.getContent());

        return builder()
                .totalPage(commentPage.getTotalPages())
                .totalCount(commentPage.getTotalElements())
                .presentPage(commentPage.getNumber())
                .comments(commentsDTO)
                .build();
    }

    private static List<SingleCommentDTO> convertToDTO(List<PostComment> comments) {
        return comments.stream()
                .map(comment -> SingleCommentDTO.of(
                        comment.getId(),
                        comment.getContent(),
                        comment.getWriterNickname())
                )
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    static class SingleCommentDTO {
        private Long commentId;
        private String content;
        private String writerNickname;

        public static SingleCommentDTO of(Long commentId, String content, String writerNickname) {
            return new SingleCommentDTO(commentId, content, writerNickname);
        }
    }
}
