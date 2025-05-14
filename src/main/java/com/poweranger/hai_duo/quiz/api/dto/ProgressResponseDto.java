package com.poweranger.hai_duo.quiz.api.dto;

import lombok.Builder;

@Builder
public record ProgressResponseDto(
        Long levelId,
        Long chapterId,
        Long stageId,
        String stageName
) {}
