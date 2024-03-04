package com.daily.daily.post.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostLike;
import com.daily.daily.post.exception.AlreadyLikeException;
import com.daily.daily.post.exception.NotPreviouslyLikedException;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.PostLikeRepository;
import com.daily.daily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void increaseLikeCount(Long memberId, Long postId) {
        if (likeRepository.findBy(memberId, postId).isPresent()) {
            throw new AlreadyLikeException();
        }

        Member findMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Post findPost = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        findPost.increaseLikeCount();
        postRepository.saveAndFlush(findPost); // 낙관적락 예외 발생 가능성 존재

        PostLike like = PostLike.builder()
                .member(findMember)
                .post(findPost)
                .build();

        likeRepository.save(like);
    }

    public void decreaseLikeCount(Long memberId, Long postId) {
        PostLike like = likeRepository.findBy(memberId, postId).orElseThrow(NotPreviouslyLikedException::new);
        Post findPost = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        findPost.decreaseLikeCount();
        postRepository.saveAndFlush(findPost); // 낙관적 락 예외 가능성 존재
        likeRepository.delete(like);
    }
}
