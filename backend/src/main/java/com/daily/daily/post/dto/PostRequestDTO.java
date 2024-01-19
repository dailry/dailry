package com.daily.daily.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    @NotNull(message = "content 필드는 반드시 존재해야 합니다.")
    private String content;

//    private List<String> hashTag;
}
