package com.daily.daily.postcomment.repository;

import com.daily.daily.postcomment.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

}
