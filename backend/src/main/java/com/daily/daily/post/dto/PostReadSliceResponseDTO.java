package com.daily.daily.post.dto;

import com.daily.daily.post.domain.Post;
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
public class PostReadSliceResponseDTO {
    private Integer presentPage;
    private Boolean hasNext;
    private List<PostReadResponseDTO> posts;


    public static PostReadSliceResponseDTO from(Slice<Post> postSlice) {
        List<PostReadResponseDTO> postReadResponseDTOS = convertToDTO(postSlice.getContent());

        return PostReadSliceResponseDTO.builder()
                .presentPage(postSlice.getNumber())
                .hasNext(postSlice.hasNext())
                .posts(postReadResponseDTOS)
                .build();
    }

    private static List<PostReadResponseDTO> convertToDTO(List<Post> posts) {
        return posts.stream()
                .map(PostReadResponseDTO::from)
                .collect(Collectors.toList());
    }
}
