package com.poweranger.hai_duo.user.api.dto;

public record UserAccuracyByLevelDto(
        Long userId,
        Long levelId,
        Integer totalCount,
        Integer correctCount,
        Float accuracyRate
) {}
