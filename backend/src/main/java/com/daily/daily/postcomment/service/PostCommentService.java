package com.daily.daily.postcomment.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.PostRepository;
import com.daily.daily.postcomment.domain.PostComment;
import com.daily.daily.postcomment.dto.PostCommentRequestDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import com.daily.daily.postcomment.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    public PostCommentResponseDTO create(Long writerId, Long postId, PostCommentRequestDTO postCommentRequestDTO) {
        Member writer = memberRepository.findById(writerId).orElseThrow(MemberNotFoundException::new);
        Post findPost = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        PostComment postComment = PostComment.builder()
                .commentWriter(writer)
                .post(findPost)
                .content(postCommentRequestDTO.getContent())
                .build();

        postCommentRepository.save(postComment);
        return PostCommentResponseDTO.from(postComment);
    }
}
