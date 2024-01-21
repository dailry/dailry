package com.daily.daily.postcomment.service;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.common.exception.UnauthorizedAccessException;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.PostRepository;
import com.daily.daily.postcomment.domain.PostComment;
import com.daily.daily.postcomment.dto.PostCommentRequestDTO;
import com.daily.daily.postcomment.dto.PostCommentResponseDTO;
import com.daily.daily.postcomment.exception.PostCommentNotFoundException;
import com.daily.daily.postcomment.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    public PostCommentResponseDTO create(Long writerId, Long postId, PostCommentRequestDTO commentRequestDTO) {
        Member writer = memberRepository.findById(writerId).orElseThrow(MemberNotFoundException::new);
        Post findPost = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        PostComment postComment = PostComment.builder()
                .commentWriter(writer)
                .post(findPost)
                .content(commentRequestDTO.getContent())
                .build();

        commentRepository.save(postComment);
        return PostCommentResponseDTO.from(postComment);
    }

    public PostCommentResponseDTO update(Long writerId, Long commentId, PostCommentRequestDTO postCommentRequestDTO) {
        PostComment findComment = commentRepository.findById(commentId).orElseThrow(PostCommentNotFoundException::new);

        validateAuthorityToComment(findComment, writerId);

        if (writerId.longValue() != findComment.getWriterId().longValue()) {
            throw new UnauthorizedAccessException();
        }

        findComment.updateContent(postCommentRequestDTO.getContent());
        return PostCommentResponseDTO.from(findComment);
    }

    public void delete(Long deleterId, Long commentId) {
        PostComment findComment = commentRepository.findById(commentId).orElseThrow(PostCommentNotFoundException::new);

        validateAuthorityToComment(findComment, deleterId);

        commentRepository.delete(findComment);
    }

    private void validateAuthorityToComment(PostComment comment, Long memberId) {
        if (comment.getWriterId().longValue() != memberId.longValue()) {
            throw new UnauthorizedAccessException();
        }
    }
}
