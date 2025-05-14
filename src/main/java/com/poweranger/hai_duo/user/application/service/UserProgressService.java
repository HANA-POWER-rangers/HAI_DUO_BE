package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.learning.domain.entity.Chapter;
import com.poweranger.hai_duo.learning.domain.entity.Level;
import com.poweranger.hai_duo.learning.domain.entity.Stage;
import com.poweranger.hai_duo.learning.domain.repository.LevelRepository;
import com.poweranger.hai_duo.quiz.api.dto.ChapterResponseDto;
import com.poweranger.hai_duo.quiz.api.dto.LevelResponseDto;
import com.poweranger.hai_duo.quiz.api.dto.StageResponseDto;
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
    private final StageRepository stageRepository;
    private final LevelRepository levelRepository;
    private final UserRepository userRepository;

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
        if(log == null) {
            throw new GeneralException(ErrorStatus.QUIZ_NOT_FOUND);
        }
    }

    public StageResponseDto getCurrentStage(Long userId) {
        Stage stage = findStageByLatestLog(userId);
        return new StageResponseDto(stage.getStageId(), stage.getStageName());
    }

    public ChapterResponseDto getCurrentChapter(Long userId) {
        Stage stage = findStageByLatestLog(userId);
        Chapter chapter = stage.getChapter();
        return new ChapterResponseDto(chapter.getChapterId());
    }

    public LevelResponseDto getCurrentLevel(Long userId) {
        Level level = findLevelByUser(userId);
        return new LevelResponseDto(level.getLevelId());
    }

    private Stage findStageByLatestLog(Long userId) {
        return stageRepository.findById(getLatestStageIdFromLog(userId))
                .orElseThrow(() -> new GeneralException(ErrorStatus.STAGE_NOT_FOUND));
    }

    private Level findLevelByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        return levelRepository.findById(user.getLevel().getLevelId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.LEVEL_NOT_FOUND));
    }

}
