package com.poweranger.hai_duo.progress.api.controller;

import com.poweranger.hai_duo.global.response.ApiResponse;
import com.poweranger.hai_duo.progress.api.dto.ChapterResponseDto;
import com.poweranger.hai_duo.progress.api.dto.LevelResponseDto;
import com.poweranger.hai_duo.progress.api.dto.ProgressResponseDto;
import com.poweranger.hai_duo.progress.api.dto.StageResponseDto;
import com.poweranger.hai_duo.progress.application.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/progress")
public class UserProgressController {

    private final UserProgressService userProgressService;

    @GetMapping("/{userId}")
    public ApiResponse<ProgressResponseDto> getCurrentProgress(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userProgressService.getCurrentProgress(userId));
    }

    @GetMapping("/{userId}/level")
    public ApiResponse<LevelResponseDto> getCurrentLevel(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userProgressService.getLatestLevel(userId));
    }

    @GetMapping("/{userId}/chapter")
    public ApiResponse<ChapterResponseDto> getCurrentChapter(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userProgressService.getLatestChapter(userId));
    }

    @GetMapping("/{userId}/stage")
    public ApiResponse<StageResponseDto> getCurrentStage(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userProgressService.getLatestStage(userId));
    }

}
