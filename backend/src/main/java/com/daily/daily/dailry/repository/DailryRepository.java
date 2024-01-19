package com.daily.daily.dailry.repository;

import com.daily.daily.dailry.domain.Dailry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailryRepository extends JpaRepository<Dailry, Long> {
}
