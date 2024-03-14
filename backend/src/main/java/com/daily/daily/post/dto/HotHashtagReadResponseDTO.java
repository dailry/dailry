package com.daily.daily.post.dto;

import com.daily.daily.post.domain.HotHashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotHashtagReadResponseDTO {
    private Long postCount;
    private String tagName;

    public static HotHashtagReadResponseDTO from(HotHashtag hotHashtag) {
        return HotHashtagReadResponseDTO.builder()
                .postCount(hotHashtag.getPostCount())
                .tagName(hotHashtag.getHashtag().getTagName())
                .build();
    }
}
