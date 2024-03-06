package com.daily.daily.post.service;

import com.daily.daily.common.config.BusinessConfig;
import com.daily.daily.member.domain.Member;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.HotPostRepository;
import com.daily.daily.post.repository.PostRepository;
import com.daily.daily.testutil.generator.MemberGenerator;
import com.daily.daily.testutil.generator.PostGenerator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    HotPostRepository hotPostRepository;

    @Autowired
    PostLikeFacade postLikeFacade;

    @Autowired
    EntityManager em;
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
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
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
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch2.countDown();
                }
            });
        }

        latch2.await();

        //then
        Post postWithDecreasedLikes = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);
        assertThat(postWithDecreasedLikes.getLikeCount()).isEqualTo(20); //50 - 30 = 20   (좋아요 증가 요청 - 좋아요 감소 요청)
    }

    @Test
    @DisplayName("post 의 좋아요가 인기글 기준치에 1만큼 못미친 상황에서 동시에 좋아요 요청이 5개 왔을 때, hot_post 테이블에 중복된 post 가 저장되면 안된다." +
            "Unique 제약조건으로 인한 예외로 좋아요 요청이 실패해서도 안된다.")
    void hotPostInsertConcurrencyTest() throws InterruptedException {
        //given
        hotPostRepository.deleteAll();

        List<Member> members = memberGenerator.generate(BusinessConfig.HOT_POST_LIKE_THRESHOLD - 1);
        Post post = postGenerator.generate();

        for (Member member : members) {
            postLikeFacade.increaseLikeCount(member.getId(), post.getId()); //좋아요 갯수를 14로 만들기. 15가 되면 인기글로 올라간다.
        }

        int concurrentRequest = 5;
        List<Member> concurrentMembers = memberGenerator.generate(concurrentRequest);

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(concurrentRequest);

        //when
        for (Member member : concurrentMembers) {
            executorService.submit(() -> {
                try {
                    postLikeFacade.increaseLikeCount(member.getId(), post.getId());
                    latch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        //then
        assertThat(hotPostRepository.count()).isEqualTo(1);
    }
}
