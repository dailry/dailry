package com.daily.daily.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

//todo : 낙관적 락 실패시 retry 하는 로직은, 여러곳에서 중복되어 사용할 수 있는 횡단 관심사 (cross-cutting concern) 이다. 추후에 AOP를 이용해서 리팩토링을 해야한다.
public class PostLikeFacade {
    private final PostLikeService postLikeService;

    public void increaseLikeCount(Long memberId, Long postId) throws InterruptedException {
        while (true) {
            try {
                postLikeService.increaseLikeCount(memberId, postId);
                break;
            } catch (OptimisticLockingFailureException e) {
                Thread.sleep(100);
            }
        }
    }

    public void decreaseLikeCount(Long memberId, Long postId) throws InterruptedException {
        while (true) {
            try {
                postLikeService.decreaseLikeCount(memberId, postId);
                break;
            } catch (OptimisticLockingFailureException e) {
                Thread.sleep(100);
            }
        }
    }
}
