package com.daily.daily.post.service;

import com.daily.daily.common.exception.UnauthorizedAccessException;
import com.daily.daily.common.domain.DirectoryPath;
import com.daily.daily.common.service.S3StorageService;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.HotHashtag;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.dto.*;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

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

    public PostWriteResponseDTO update(Long memberId, Long postId, PostWriteRequestDTO postWriteRequestDTO, MultipartFile pageImage) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        validateAuthorityToPost(post, memberId);
        post.updateContent(postWriteRequestDTO.getContent());
        hashtagService.updateHashtagsInPost(post, postWriteRequestDTO.getHashtags());

        if (pageImage == null || pageImage.isEmpty()) {
            return PostWriteResponseDTO.from(post);
        }

        uploadPageImage(post, pageImage);
        return PostWriteResponseDTO.from(post);
    }

    private void uploadPageImage(Post post, MultipartFile pageImage) {
        DirectoryPath dirPath = DirectoryPath.of("post",
                LocalDate.now(ZoneId.of("Asia/Seoul")).toString(),
                post.getId().toString());

        URI fileUri = storageService.uploadImage(pageImage, dirPath, UUID.randomUUID() + ".png");

        post.updatePageImage(fileUri.toString());
    }

    public PostReadResponseDTO find(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return PostReadResponseDTO.from(post);
    }

    public PostReadSliceResponseDTO findSlice(Pageable pageable) {
        Slice<Post> posts = postRepository.findSlice(pageable);
        return PostReadSliceResponseDTO.from(posts);
    }

    public void delete(Long memberId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        validateAuthorityToPost(post, memberId);

        postRepository.deletePostAndRelatedEntities(postId);
    }


    private void validateAuthorityToPost(Post post, Long writerId) {
        if (!post.isWrittenBy(writerId)) {
            throw new UnauthorizedAccessException();
        }
    }

    public PostReadSliceResponseDTO findPostByHashtag(List<String> hashtags, Pageable pageable) {
        Slice<Post> posts = postRepository.findPostsByHashtag(hashtags, pageable);
        return PostReadSliceResponseDTO.from(posts);
    }

    public PostReadSliceResponseDTO findPostByMember(Long memberId, Pageable pageable) {
        Slice<Post> posts = postRepository.findPostsByHashtag(memberId, pageable);
        return PostReadSliceResponseDTO.from(posts);
    }
}
