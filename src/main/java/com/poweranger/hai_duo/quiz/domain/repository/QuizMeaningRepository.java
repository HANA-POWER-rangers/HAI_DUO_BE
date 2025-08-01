package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.domain.entity.QuizMeaning;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface QuizMeaningRepository extends JpaRepository<QuizMeaning, Long> {

    List<QuizMeaning> findAllByStage(Stage stage);
    Optional<QuizMeaning> findByStage_StageId(Long stageId);
}
