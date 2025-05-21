package com.poweranger.hai_duo.progress.domain.repository;

import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
