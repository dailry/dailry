package com.daily.daily.post.dto;

import com.daily.daily.post.domain.HotHashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotHashtagReadResponseDTO {
    private List<HotHashtag> hashtags;

    public static HotHashtagReadResponseDTO from(List<HotHashtag> hashtags) {
        System.out.println(hashtags);
        return HotHashtagReadResponseDTO.builder()
                .hashtags(hashtags)
                .build();
    }
}
