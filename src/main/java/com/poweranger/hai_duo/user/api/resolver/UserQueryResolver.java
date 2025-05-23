package com.poweranger.hai_duo.user.api.resolver;

import com.poweranger.hai_duo.user.api.dto.*;
import com.poweranger.hai_duo.user.application.service.UserAccuracyService;
import com.poweranger.hai_duo.user.application.service.UserService;
import com.poweranger.hai_duo.user.domain.entity.mongodb.UserProgressLog;
import com.poweranger.hai_duo.user.domain.repository.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserQueryResolver {

    private final UserService userService;
    private final UserProgressRepository userProgressRepository;
    private final UserAccuracyService userAccuracyService;

    @QueryMapping
    public UserDto getUserById(@Argument Long userId) {
        return userService.getUserDtoById(userId);
    }

    @QueryMapping
    public List<UserProgressLog> getUserQuizLogs(@Argument Long userId) {
        return userProgressRepository.findByUserId(userId);
    }

    @QueryMapping
    public UserAccuracyDto getUserAccuracy(@Argument Long userId) {
        return userAccuracyService.getUserAccuracy(userId);
    }

    @QueryMapping
    public UserAccuracyByStageDto getUserAccuracyByStage(@Argument Long userId, @Argument Long stageId) {
        return userAccuracyService.getUserAccuracyByStage(userId, stageId);
    }

    @QueryMapping
    public UserAccuracyByChapterDto getUserAccuracyByChapter(@Argument Long userId, @Argument Long chapterId) {
        return userAccuracyService.getUserAccuracyByChapter(userId, chapterId);
    }

    @QueryMapping
    public UserAccuracyByLevelDto getUserAccuracyByLevel(@Argument Long userId, @Argument Long levelId) {
        return userAccuracyService.getUserAccuracyByLevel(userId, levelId);
    }
}
