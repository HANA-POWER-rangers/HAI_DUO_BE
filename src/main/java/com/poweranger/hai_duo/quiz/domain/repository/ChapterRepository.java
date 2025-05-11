package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.learning.domain.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
