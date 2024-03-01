package com.daily.daily.post.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.PostRepository;
import com.daily.daily.testutil.generator.MemberGenerator;
import com.daily.daily.testutil.generator.PostGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostLikeServiceConcurrencyTest {

    @Autowired
    MemberGenerator memberGenerator;

    @Autowired
    PostGenerator postGenerator;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeFacade postLikeFacade;
    @Test
    @DisplayName("동시에 좋아요 요청이 50개 왔을 때, 좋아요의 갯수가 50이 오르는지 확인한다. " +
            "그리고 이후에 좋아요 해제 요청이 동시에 30개가 왔을 때 전체 좋아요의 갯수는 20이어야 한다.")
    void likeIncreaseTest() throws InterruptedException {
        //테스트1 : 동시에 좋아요 요청이 50개 왔을 때, 좋아요의 갯수가 50이 오르는지 확인한다.
        //given
        int likeIncreaseRequest = 50; // 동시 요청 횟수
        List<Member> members = memberGenerator.generate(likeIncreaseRequest);
        Post post = postGenerator.generate();

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(likeIncreaseRequest);

        //when
        for (Member member : members) {
            executorService.submit(() -> {
                try {
                    postLikeFacade.increaseLikeCount(member.getId(), post.getId());
                    latch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        latch.await();

        //then
        Post postWithIncreasedLikes = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);
        assertThat(postWithIncreasedLikes.getLikeCount()).isEqualTo(likeIncreaseRequest);

        //테스트 2 : 이후에 좋아요 해제 요청이 동시에 30개가 왔을 때 전체 좋아요의 갯수는 20이어야 한다.
        //given
        int likeDecreaseRequest = 30;
        CountDownLatch latch2 = new CountDownLatch(likeDecreaseRequest);
        List<Member> likeDecreaseMembers = members.subList(0, 30);

        //when
        for (Member member : likeDecreaseMembers) {
            executorService.submit(() -> {
                try {
                    postLikeFacade.decreaseLikeCount(member.getId(), post.getId());
                    latch2.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        latch2.await();

        //then
        Post postWithDecreasedLikes = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);
        assertThat(postWithDecreasedLikes.getLikeCount()).isEqualTo(20); //50 - 30 = 20   (좋아요 증가 요청 - 좋아요 감소 요청)
    }
}
