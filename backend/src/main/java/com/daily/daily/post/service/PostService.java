package com.daily.daily.post.service;

import com.daily.daily.common.service.S3StorageService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.dto.PostReadResponseDTO;
import com.daily.daily.post.dto.PostWriteRequestDTO;
import com.daily.daily.post.dto.PostWriteResponseDTO;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.PostLikeRepository;
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

    private final PostLikeRepository likeRepository;

    private final S3StorageService storageService;

    private final HashtagService hashtagService;

    public PostWriteResponseDTO create(Long memberId, PostWriteRequestDTO postWriteRequestDTO, MultipartFile pageImage) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Post post = Post.builder()
                .content(postWriteRequestDTO.getContent())
                .postWriter(member)
                .build();

        Post savedPost = postRepository.save(post);
        hashtagService.addHashtagsToPost(savedPost, postWriteRequestDTO.getHashtags());

        uploadPageImage(savedPost, pageImage);
        return PostWriteResponseDTO.from(savedPost);
    }

    public PostWriteResponseDTO update(Long postId, PostWriteRequestDTO postWriteRequestDTO, MultipartFile pageImage) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        post.updateContent(postWriteRequestDTO.getContent());
        hashtagService.updateHashtagsInPost(post, postWriteRequestDTO.getHashtags());

        if (pageImage == null || pageImage.isEmpty()) {
            return PostWriteResponseDTO.from(post);
        }

        uploadPageImage(post, pageImage);
        return PostWriteResponseDTO.from(post);
    }

    private void uploadPageImage(Post post, MultipartFile pageImage) {
        String fileUrl = storageService.uploadImage(pageImage, POST_STORAGE_DIRECTORY_PATH, post.getId().toString());
        post.updatePageImage(fileUrl);
    }

    public PostReadResponseDTO find(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Long likeCount = likeRepository.countByPost(post);

        return PostReadResponseDTO.from(post, likeCount);
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
