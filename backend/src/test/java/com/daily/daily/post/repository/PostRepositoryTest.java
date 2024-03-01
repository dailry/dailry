package com.daily.daily.post.repository;

import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostHashtag;
import com.daily.daily.post.domain.PostLike;
import com.daily.daily.postcomment.domain.PostComment;
import com.daily.daily.testutil.config.JpaTest;
import com.daily.daily.testutil.generator.HashtagGenerator;
import com.daily.daily.testutil.generator.PostCommentGenerator;
import com.daily.daily.testutil.generator.PostGenerator;
import com.daily.daily.testutil.generator.PostLikeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRepositoryTest extends JpaTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    HashtagRepository hashtagRepository;

    @Autowired
    PostGenerator postGenerator;

    @Autowired
    PostCommentGenerator postCommentGenerator;

    @Autowired
    PostLikeGenerator postLikeGenerator;

    @Autowired
    HashtagGenerator hashtagGenerator;

    @Autowired
    TestEntityManager testEntityManager;
    @Test
    @DisplayName("게시글 삭제 요청이 들어왔을 때, 게시글을 외래키로 참조하는 다른 레코드들도 삭제가 되는지 확인한다.")
    void deletePostAndRelatedEntities() {
        //given
        Post post = postGenerator.generate();
        List<PostComment> comments = postCommentGenerator.generate(post, 5);// 댓글 5개 생성
        List<PostLike> likes = postLikeGenerator.generate(post, 3);// 좋아요 3개 생성
        List<PostHashtag> postHashtags = hashtagGenerator.generate(post, "일반", "대학생", "주말");

        //when
        postRepository.deletePostAndRelatedEntities(post.getId());
        testEntityManager.flush();
        testEntityManager.clear();

        //then
        //좋아요, 게시글에 달린 해시태그, 댓글, 게시글 << 이 4개 모두 삭제되어야 함.
        for (PostComment postComment : comments) {      // 댓글 삭제 확인
            assertThat(testEntityManager.find(PostComment.class, postComment.getId())).isNull();
        }
        for (PostLike like : likes) {                   // 좋아요 삭제 확인
            assertThat(testEntityManager.find(PostLike.class, like.getId())).isNull();
        }
        for (PostHashtag postHashtag : postHashtags) {  // 게시글에 연결된 해시태그 삭제 확인
            assertThat(testEntityManager.find(PostHashtag.class, postHashtag.getId())).isNull();
        }

        //단 해시태그 데이터 자체는 남아있어야 한다.
        assertThat(hashtagRepository.findByTagName("일반").isPresent()).isTrue();
        assertThat(hashtagRepository.findByTagName("대학생").isPresent()).isTrue();
        assertThat(hashtagRepository.findByTagName("주말").isPresent()).isTrue();
    }
}
