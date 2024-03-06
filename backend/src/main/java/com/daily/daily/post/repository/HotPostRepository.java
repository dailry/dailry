package com.daily.daily.post.repository;

import com.daily.daily.post.domain.HotPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotPostRepository extends JpaRepository<HotPost, Long>, HotPostRepositoryQuerydsl {
}
