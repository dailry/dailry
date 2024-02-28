package com.daily.daily.post.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostLikeServiceConcurrencyTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostLikeService postLikeService;

    @Test
    @DisplayName("동시에 좋아요 요청이 50개 왔을 때, 좋아요의 갯수가 50이 오르는지 확인한다.")
    void likeIncreaseTest() throws InterruptedException {
        //given
        int concurrentRequestCount  = 50; // 동시 요청 횟수
        List<Member> members = generateMembers(concurrentRequestCount);
        Post post = generatePost();

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(concurrentRequestCount );

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
        assertThat(findPost.getLikeCount()).isEqualTo(concurrentRequestCount );
    }

    List<Member> generateMembers(int memberNum) {
        List<Member> testMembers = new ArrayList<>();

        for (int i = 0; i < memberNum; i++) {
            Member member = Member.builder().username("동시성 테스트 유저" + i).build();
            testMembers.add(memberRepository.save(member));
        }
        return testMembers;
    }

    Post generatePost() {
        Member writer = Member.builder().build();
        Post post = Post.builder().postWriter(writer).build();

        memberRepository.save(writer);
        postRepository.save(post);
        return post;
    }
}
