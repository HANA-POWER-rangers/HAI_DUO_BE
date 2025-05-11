package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.learning.domain.entity.Chapter;
import com.poweranger.hai_duo.learning.domain.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findAllByChapter(Chapter chapter);
}
