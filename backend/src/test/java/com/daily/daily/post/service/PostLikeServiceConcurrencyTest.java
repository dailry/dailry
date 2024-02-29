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
    PostLikeService postLikeService;


    @Test
    @DisplayName("동시에 좋아요 요청이 50개 왔을 때, 좋아요의 갯수가 50이 오르는지 확인한다.")
    void likeIncreaseTest() throws InterruptedException {
        //given
        int concurrentRequestCount = 50; // 동시 요청 횟수
        List<Member> members = memberGenerator.generate(concurrentRequestCount);
        Post post = postGenerator.generate();

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(concurrentRequestCount);

        //when
        for (Member member : members) {
            executorService.submit(() -> {
                postLikeService.increaseLikeCount(member.getId(), post.getId());
                latch.countDown();
            });
        }

        latch.await();

        //then
        Post findPost = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);
        assertThat(findPost.getLikeCount()).isEqualTo(concurrentRequestCount);
    }
}
