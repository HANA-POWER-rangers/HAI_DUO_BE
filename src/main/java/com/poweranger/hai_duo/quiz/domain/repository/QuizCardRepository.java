package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.domain.entity.QuizCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuizCardRepository extends JpaRepository<QuizCard, Long> {

    Optional<QuizCard> findByStage(Stage stage);

    Optional<QuizCard> findByStage_StageId(Long stageId);
}
