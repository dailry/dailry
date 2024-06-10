package com.daily.daily.post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostWriteRequestDTO {
    @NotNull(message = "content 필드는 반드시 존재해야 합니다.")
    private String content;

    @Size(max = 5, message = "게시글에 붙일 수 있는 해시태그는 최대 5개 입니다.")
    private List<String> hashtags;
}
