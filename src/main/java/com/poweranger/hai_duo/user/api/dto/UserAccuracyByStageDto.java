package com.poweranger.hai_duo.user.api.dto;

public record UserAccuracyByStageDto(
        Long userId,
        Long stageId,
        Integer totalCount,
        Integer correctCount,
        Float accuracyRate
) {
}
