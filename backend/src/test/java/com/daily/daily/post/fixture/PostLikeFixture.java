package com.daily.daily.post.fixture;

import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostLike;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class PostLikeFixture {

    public static List<PostLike> 좋아요_이력_리스트(int postIdStart, int postIdEnd) {
        List<PostLike> result = new ArrayList<>();

        for (int i = postIdStart; i <= postIdEnd; i++) {
            Post post = Mockito.spy(Post.builder().build());

            when(post.getId()).thenReturn((long) i);

            result.add(PostLike.builder().post(post).build());
        }

        return result;
    }
}
