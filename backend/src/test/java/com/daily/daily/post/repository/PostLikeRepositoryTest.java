package com.daily.daily.post.repository;

import com.daily.daily.member.domain.Member;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostLike;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PostLikeRepositoryTest {

    @Autowired
    PostLikeRepository likeRepository;

    @Autowired
    TestEntityManager testEntityManager;
    private final Member 테스트_멤버 = Member
            .builder()
            .build();
    private final Post 테스트_게시글 = Post
            .builder()
            .postWriter(테스트_멤버)
            .build();

    @Nested
    @DisplayName("findBy() 좋아요 이력 존재 여부 메서드 테스트")
    class findBy {
        @Test
        @DisplayName("좋아요 이력이 존재하는 경우, findBy() 메서드는 PostLike 객체를 올바르게 반환해야 한다.")
        void test1() {
            //given
            PostLike like = PostLike.builder()
                    .post(테스트_게시글)
                    .member(테스트_멤버)
                    .build();

            testEntityManager.persist(테스트_게시글);
            testEntityManager.persist(테스트_멤버);
            likeRepository.save(like);

            //when
            PostLike findLike = likeRepository.findBy(테스트_멤버.getId(), 테스트_게시글.getId())
                    .orElseThrow(IllegalArgumentException::new);

            //then
            Assertions.assertThat(findLike).isEqualTo(like);
        }

        @Test
        @DisplayName("좋아요 이력이 존재하지 않는경우, findBy()메서드는 빈 Optional을 반환해야 한다.")
        void test2() {
            //when
            boolean isEmpty = likeRepository.findBy(3L, 5L).isEmpty();
            //then
            Assertions.assertThat(isEmpty).isTrue();
        }
    }
}