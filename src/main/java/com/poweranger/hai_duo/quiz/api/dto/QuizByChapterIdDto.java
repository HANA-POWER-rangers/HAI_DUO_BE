package com.poweranger.hai_duo.quiz.api.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record QuizByChapterIdDto(
        Long chapterId,
        List<QuizByStageIdDto> quizzes
) {}
