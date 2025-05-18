package com.poweranger.hai_duo.progress.application.reader;

import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import com.poweranger.hai_duo.progress.domain.entity.Level;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.progress.domain.repository.LevelRepository;
import com.poweranger.hai_duo.progress.domain.repository.StageRepository;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;
import com.poweranger.hai_duo.user.domain.repository.UserRepository;
import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgressReader {

    private final StageRepository stageRepository;
    private final LevelRepository levelRepository;
    private final UserRepository userRepository;

    public Stage getStage(Long stageId) {
        return stageRepository.findById(stageId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STAGE_NOT_FOUND));
    }

    public Chapter getChapterByStage(Stage stage) {
        return stage.getChapter();
    }

    public Level getLevelByUser(User user) {
        return levelRepository.findById(user.getLevel().getLevelId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.LEVEL_NOT_FOUND));
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
}
