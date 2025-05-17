package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.application.reader.ProgressReader;
import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import com.poweranger.hai_duo.progress.domain.entity.Level;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.progress.api.dto.ChapterResponseDto;
import com.poweranger.hai_duo.progress.api.dto.LevelResponseDto;
import com.poweranger.hai_duo.progress.api.dto.ProgressResponseDto;
import com.poweranger.hai_duo.progress.api.dto.StageResponseDto;
import com.poweranger.hai_duo.progress.api.factory.ProgressDtoFactory;
import com.poweranger.hai_duo.user.api.dto.UserProgressLogDto;
import com.poweranger.hai_duo.user.domain.entity.mongodb.UserProgressLog;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProgressService {

    private final MongoTemplate mongoTemplate;
    private final ProgressReader progressReader;
    private final ProgressDtoFactory progressDtoFactory;

    public Long getLatestStageIdFromLog(Long userId) {
        UserProgressLog log = mongoTemplate.findOne(
                Query.query(Criteria.where("userId").is(userId))
                        .with(Sort.by(Sort.Direction.DESC, "answeredAt"))
                        .limit(1),
                UserProgressLog.class
        );
        validateLogExists(log);
        return log.getStageId();
    }

    private void validateLogExists(UserProgressLog log) {
        if (log == null) {
            throw new GeneralException(ErrorStatus.QUIZ_NOT_FOUND);
        }
    }

    public StageResponseDto getLatestStage(Long userId) {
        Stage stage = findLatestStage(userId);
        return new StageResponseDto(stage.getStageId(), stage.getStageName());
    }

    public ChapterResponseDto getLatestChapter(Long userId) {
        Stage stage = findLatestStage(userId);
        Chapter chapter = progressReader.getChapterByStage(stage);
        return new ChapterResponseDto(chapter.getChapterId());
    }

    public LevelResponseDto getLatestLevel(Long userId) {
        User user = progressReader.getUser(userId);
        Level level = progressReader.getLevelByUser(user);
        return new LevelResponseDto(level.getLevelId());
    }

    public ProgressResponseDto getCurrentProgress(Long userId) {
        Stage stage = findLatestStage(userId);
        Chapter chapter = progressReader.getChapterByStage(stage);
        User user = progressReader.getUser(userId);
        Level level = progressReader.getLevelByUser(user);
        return progressDtoFactory.toProgressResponseDto(level, chapter, stage);
    }

    private Stage findLatestStage(Long userId) {
        Long stageId = getLatestStageIdFromLog(userId);
        return progressReader.getStage(stageId);
    }

}
