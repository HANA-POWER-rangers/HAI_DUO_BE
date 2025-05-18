package com.poweranger.hai_duo.user.api.dto;

public record UserAccuracyByChapterDto(
        Long userId,
        Long chapterId,
        Integer totalCount,
        Integer correctCount,
        Float accuracyRate
) {
}
