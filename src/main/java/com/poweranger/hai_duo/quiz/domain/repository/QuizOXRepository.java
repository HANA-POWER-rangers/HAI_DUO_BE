package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.learning.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.domain.entity.QuizOX;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface QuizOXRepository extends CrudRepository<QuizOX, Long> {
    Optional<QuizOX> findByStage(Stage stage);
}
