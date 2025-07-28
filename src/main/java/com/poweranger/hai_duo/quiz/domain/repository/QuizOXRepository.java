package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.domain.entity.QuizOX;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface QuizOXRepository extends CrudRepository<QuizOX, Long> {

    List<QuizOX> findAllByStage(Stage stage);
    Optional<QuizOX> findByStage_StageId(Long stageId);
}
