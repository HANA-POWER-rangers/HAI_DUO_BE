package com.poweranger.hai_duo.user.api.dto;

import com.poweranger.hai_duo.quiz.domain.entity.QuizType;
import java.time.LocalDateTime;

public record UserProgressLogDto(
        String id,
        Long userId,
        Long stageId,
        QuizType quizType,
        boolean isCorrect,
        String selectedOption,
        String answer,
        Float responseTime,
        LocalDateTime answeredAt
) {
}
