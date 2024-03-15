package com.daily.daily.post.repository;

import com.daily.daily.post.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>, HotHashtagRepositoryQuerydsl {
    Optional<Hashtag> findByTagName(String tagName);
}