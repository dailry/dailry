package com.daily.daily.post.dto;

import com.daily.daily.post.domain.HotPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class HotPostReadSliceResponseDTO {
    private Integer presentPage;
    private Boolean hasNext;
    private List<HotPostReadResponseDTO> hotPosts;
    public static HotPostReadSliceResponseDTO from(Slice<HotPost> hotPosts) {
        List<HotPostReadResponseDTO> hotPostReadResponseDTOS = convertToDTO(hotPosts.getContent());

        return HotPostReadSliceResponseDTO.builder()
                .presentPage(hotPosts.getNumber())
                .hasNext(hotPosts.hasNext())
                .hotPosts(hotPostReadResponseDTOS)
                .build();
    }

    private static List<HotPostReadResponseDTO> convertToDTO(List<HotPost> hotPosts) {
        return hotPosts.stream()
                .map(HotPostReadResponseDTO::from)
                .collect(Collectors.toList());
    }


}
