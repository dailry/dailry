package com.daily.daily.post.dto;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.MemberInfoDTO;
import com.daily.daily.post.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode
public class PostResponseDTO {
    private Long postId;
    private String content;
    private String pageImage;
    private Long memberId;
    private String writer;
    private String nickname;
    private LocalDateTime createdTime;

    public static PostResponseDTO from(Post post) {
        Member member = post.getMember();

        return PostResponseDTO.builder()
                .postId(post.getId())
                .content(post.getContent())
                .pageImage(post.getPageImage())
                .memberId(member.getId())
                .writer(member.getNickname())
                .createdTime(post.getCreatedTime())
                .build();
    }
}
