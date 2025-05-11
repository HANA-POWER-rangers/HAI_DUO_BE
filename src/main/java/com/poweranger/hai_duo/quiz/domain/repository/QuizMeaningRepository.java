package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.quiz.domain.entity.QuizMeaning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizMeaningRepository extends JpaRepository<QuizMeaning, Long> {
}
