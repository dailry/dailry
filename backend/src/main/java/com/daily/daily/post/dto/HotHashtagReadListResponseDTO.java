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
        for(HotHashtag hash : hashtags) {
            System.out.println("HotHashtagReadListResponseDTO1 : " + hash.getHashtag().getTagName());
            System.out.println("HotHashtagReadListResponseDTO1 : " + hash.getHashtag().getId());
        }

        List<HotHashtagReadResponseDTO> responseDTOs = hashtags.stream()
                .map(HotHashtagReadResponseDTO::from)
                .collect(Collectors.toList());

        System.out.println("responseDTOs : " + responseDTOs);
        return HotHashtagReadListResponseDTO.builder()
                .hashtags(responseDTOs)
                .build();
    }
}
