package com.daily.daily.post.repository;

import com.daily.daily.post.domain.HotHashtag;

import java.util.List;

public interface HotHashtagRepositoryQuerydsl {
    List<HotHashtag> findHotHashTags();
}
