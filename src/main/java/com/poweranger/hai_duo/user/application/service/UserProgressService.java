package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.application.reader.ProgressReader;
import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import com.poweranger.hai_duo.progress.domain.entity.Level;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.progress.domain.repository.LevelRepository;
import com.poweranger.hai_duo.progress.api.dto.ChapterResponseDto;
import com.poweranger.hai_duo.progress.api.dto.LevelResponseDto;
import com.poweranger.hai_duo.progress.api.dto.ProgressResponseDto;
import com.poweranger.hai_duo.progress.api.dto.StageResponseDto;
import com.poweranger.hai_duo.progress.api.factory.ProgressDtoFactory;
import com.poweranger.hai_duo.quiz.domain.repository.StageRepository;
import com.poweranger.hai_duo.user.domain.entity.mongodb.UserQuizLog;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;
import com.poweranger.hai_duo.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProgressService {

    private final MongoTemplate mongoTemplate;
    private final ProgressReader progressReader;
    private final ProgressDtoFactory progressDtoFactory;

    public Long getLatestStageIdFromLog(Long userId) {
        UserQuizLog log = mongoTemplate.findOne(
                Query.query(Criteria.where("userId").is(userId))
                        .with(Sort.by(Sort.Direction.DESC, "answeredAt"))
                        .limit(1),
                UserQuizLog.class
        );
        validateLogExists(log);
        return log.getStageId();
    }

    private void validateLogExists(UserQuizLog log) {
        if (log == null) {
            throw new GeneralException(ErrorStatus.QUIZ_NOT_FOUND);
        }
    }

    public StageResponseDto getCurrentStage(Long userId) {
        Stage stage = getLatestStage(userId);
        return new StageResponseDto(stage.getStageId(), stage.getStageName());
    }

    public ChapterResponseDto getCurrentChapter(Long userId) {
        Stage stage = getLatestStage(userId);
        Chapter chapter = progressReader.getChapterByStage(stage);
        return new ChapterResponseDto(chapter.getChapterId());
    }

    public LevelResponseDto getCurrentLevel(Long userId) {
        User user = progressReader.getUser(userId);
        Level level = progressReader.getLevelByUser(user);
        return new LevelResponseDto(level.getLevelId());
    }

    public ProgressResponseDto getCurrentProgress(Long userId) {
        Stage stage = getLatestStage(userId);
        Chapter chapter = progressReader.getChapterByStage(stage);
        User user = progressReader.getUser(userId);
        Level level = progressReader.getLevelByUser(user);
        return progressDtoFactory.toProgressResponseDto(level, chapter, stage);
    }

    private Stage getLatestStage(Long userId) {
        Long stageId = getLatestStageIdFromLog(userId);
        return progressReader.getStage(stageId);
    }
}
