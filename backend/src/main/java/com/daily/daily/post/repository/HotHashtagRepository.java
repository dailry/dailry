package com.daily.daily.post.repository;

import com.daily.daily.post.domain.HotHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotHashtagRepository extends JpaRepository<HotHashtag, Long>, HotHashtagRepositoryQuerydsl {
}