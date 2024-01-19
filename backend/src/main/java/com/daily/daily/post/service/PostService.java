package com.daily.daily.post.service;

import com.daily.daily.common.service.S3StorageService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.dto.PostRequestDTO;
import com.daily.daily.post.dto.PostResponseDTO;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final String POST_STORAGE_DIRECTORY_PATH = "post/" + LocalDate.now(ZoneId.of("Asia/Seoul"));

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final S3StorageService storageService;


    public PostResponseDTO create(Long memberId, PostRequestDTO postRequestDTO, MultipartFile pageImage) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        Post post = Post.builder()
                .content(postRequestDTO.getContent())
                .member(member)
                .build();

        Post savedPost = postRepository.save(post);

        String fileUrl = storageService.uploadImage(pageImage, POST_STORAGE_DIRECTORY_PATH, savedPost.getId().toString());
        savedPost.updatePageImage(fileUrl);

        return PostResponseDTO.from(savedPost);
    }

    public PostResponseDTO update(Long postId, PostRequestDTO postRequestDTO, MultipartFile pageImage) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        post.updateContent(postRequestDTO.getContent());

        if (pageImage == null || pageImage.isEmpty()) {
            return PostResponseDTO.from(post);
        }

        String fileUrl = storageService.uploadImage(pageImage, POST_STORAGE_DIRECTORY_PATH, post.getId().toString());
        post.updatePageImage(fileUrl);
        return PostResponseDTO.from(post);
    }

    public PostResponseDTO find(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostResponseDTO.from(post);
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

}
