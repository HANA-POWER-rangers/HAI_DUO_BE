package com.poweranger.hai_duo.quiz.api.controller;

import com.poweranger.hai_duo.progress.api.dto.ChapterResponseDto;
import com.poweranger.hai_duo.progress.api.dto.LevelResponseDto;
import com.poweranger.hai_duo.quiz.api.dto.ProgressResponseDto;
import com.poweranger.hai_duo.progress.api.dto.StageResponseDto;
import com.poweranger.hai_duo.user.application.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProgressResponseDto> getCurrentProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.getCurrentProgress(userId));
    }

    @GetMapping("/{userId}/level")
    public ResponseEntity<LevelResponseDto> getCurrentLevel(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.getCurrentLevel(userId));
    }

    @GetMapping("/{userId}/chapter")
    public ResponseEntity<ChapterResponseDto> getCurrentChapter(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.getCurrentChapter(userId));
    }

    @GetMapping("/{userId}/stage")
    public ResponseEntity<StageResponseDto> getCurrentStage(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.getCurrentStage(userId));
    }

}
