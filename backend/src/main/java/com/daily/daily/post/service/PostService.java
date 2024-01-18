package com.daily.daily.post.service;

import com.daily.daily.common.service.S3StorageService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.dto.PostCreateDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import com.daily.daily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final S3StorageService storageService;
    private final String POST_STORAGE_DIRECTORY_PATH = "post/";
    public PostResponseDTO create(Long memberId, PostCreateDTO postCreateDTO, MultipartFile pageImage) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        String filePath = storageService.uploadImage(pageImage, POST_STORAGE_DIRECTORY_PATH);
        String content = postCreateDTO.getContent();

        Post post = Post.builder()
                .content(content)
                .pageImage(filePath)
                .member(member)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponseDTO.from(savedPost);
    }
}
