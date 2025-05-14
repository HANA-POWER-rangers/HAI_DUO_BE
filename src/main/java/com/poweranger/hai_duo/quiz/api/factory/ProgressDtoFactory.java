package com.poweranger.hai_duo.quiz.api.factory;

import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import com.poweranger.hai_duo.progress.domain.entity.Level;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.api.dto.ProgressResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProgressDtoFactory {

    public ProgressResponseDto toProgressResponseDto(Level level, Chapter chapter, Stage stage) {
        return ProgressResponseDto.builder()
                .levelId(level.getLevelId())
                .chapterId(chapter.getChapterId())
                .stageId(stage.getStageId())
                .stageName(stage.getStageName())
                .build();
    }
}
