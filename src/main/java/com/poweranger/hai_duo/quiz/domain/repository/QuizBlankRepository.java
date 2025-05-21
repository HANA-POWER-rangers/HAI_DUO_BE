package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.domain.entity.QuizBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuizBlankRepository extends JpaRepository<QuizBlank, Long> {

    Optional<QuizBlank> findByStage(Stage stage);

    Optional<QuizBlank> findByStage_StageId(Long stageId);
}
