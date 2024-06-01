package com.daily.daily.post.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.HotPost;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostLike;
import com.daily.daily.post.exception.AlreadyLikeException;
import com.daily.daily.post.exception.NotPreviouslyLikedException;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.HotPostRepository;
import com.daily.daily.post.repository.PostLikeRepository;
import com.daily.daily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final HotPostRepository hotPostRepository;
    public void increaseLikeCount(Long memberId, Long postId) {
        if (likeRepository.findBy(memberId, postId).isPresent()) {
            throw new AlreadyLikeException();
        }

        Member findMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Post findPost = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        findPost.increaseLikeCount();
        postRepository.saveAndFlush(findPost); // 낙관적락 예외 발생 가능성 존재

        makeHotPostIfPossible(findPost);
        saveLikeHistory(findMember, findPost);
    }

    private void makeHotPostIfPossible(Post findPost) {
        if (findPost.isHotPost()) return;
        if (findPost.satisfyHotPostCondition()) {
            findPost.makeHotPost();
            hotPostRepository.save(HotPost.of(findPost));
        }
    }

    private void saveLikeHistory(Member findMember, Post findPost) {
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

    public Map<Long, Boolean> getLikeStatus(Long memberId, List<Long> postIds) {
        List<PostLike> postLikes = likeRepository.findByMemberIdAndPostIds(memberId, postIds);

        Map<Long, Boolean> result = new HashMap<>();

        postLikes.forEach(postLike -> result.put(postLike.getPostId(), true));
        postIds.forEach(postId -> result.putIfAbsent(postId, false));
        return result;
    }
}
