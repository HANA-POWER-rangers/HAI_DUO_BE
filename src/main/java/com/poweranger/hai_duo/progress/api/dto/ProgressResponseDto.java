package com.poweranger.hai_duo.progress.api.dto;

import lombok.Builder;

@Builder
public record ProgressResponseDto(
        Long levelId,
        Long chapterId,
        Long stageId,
        String stageName
) {}
