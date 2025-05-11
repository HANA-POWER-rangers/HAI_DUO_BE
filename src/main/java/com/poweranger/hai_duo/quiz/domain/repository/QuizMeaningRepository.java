package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.learning.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.domain.entity.QuizMeaning;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuizMeaningRepository extends JpaRepository<QuizMeaning, Long> {
    Optional<QuizMeaning> findByStage(Stage stage);
}
