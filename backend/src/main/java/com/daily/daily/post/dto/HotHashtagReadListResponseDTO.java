package com.daily.daily.post.dto;

import com.daily.daily.post.domain.HotHashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotHashtagReadListResponseDTO {
    private List<HotHashtagReadResponseDTO> hashtags;

    public static HotHashtagReadListResponseDTO from(List<HotHashtag> hashtags) {
        List<HotHashtagReadResponseDTO> responseDTOs = hashtags.stream()
                .map(HotHashtagReadResponseDTO::from)
                .collect(Collectors.toList());

        return HotHashtagReadListResponseDTO.builder()
                .hashtags(responseDTOs)
                .build();
    }
}
