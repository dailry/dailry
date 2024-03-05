package com.daily.daily.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostReadByHashTagRequestDTO {
    private List<String> hashtags;
}
