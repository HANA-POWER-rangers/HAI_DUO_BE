package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.domain.repository.StageRepository;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyByChapterDto;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyByStageDto;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyDto;
import com.poweranger.hai_duo.user.api.factory.UserAccuracyDtoFactory;
import com.poweranger.hai_duo.user.api.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccuracyService {

    private final MongoTemplate mongoTemplate;
    private final StageRepository stageRepository;
    private final UserAccuracyDtoFactory userAccuracyDtoFactory;

    public UserAccuracyDto getUserAccuracy(Long userId) {
        Aggregation aggregation = buildUserAggregation(userId);
        Document result = executeAggregation(aggregation);
        return userAccuracyDtoFactory.buildUserAccuracyDto(result);
    }

    public UserAccuracyByStageDto getUserAccuracyByStage(Long userId, Long stageId) {
        Aggregation aggregation = buildStageAggregation(userId, stageId);
        Document result = executeAggregation(aggregation);
        return userAccuracyDtoFactory.buildUserAccuracyDtoByStage(result);
    }

    public UserAccuracyByChapterDto getUserAccuracyByChapter(Long userId, Long chapterId) {
        List<Long> stageIds = stageRepository.findStageIdsByChapterId(chapterId);
        validateStageIds(stageIds);

        Aggregation aggregation = buildChapterAggregation(userId, stageIds);
        Document result = executeAggregation(aggregation);
        return userAccuracyDtoFactory.buildUserAccuracyDtoByChapter(result, chapterId);
    }

    // --- Aggregation Builders ---

    private Aggregation buildUserAggregation(Long userId) {
        return Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)),
                buildGroupOperation("userId")
        );
    }

    private Aggregation buildStageAggregation(Long userId, Long stageId) {
        return Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId).and("stageId").is(stageId.intValue())),
                Aggregation.group("stageId")
                        .first("userId").as("userId")
                        .first("stageId").as("stageId")
                        .count().as("totalCount")
                        .sum(getCorrectCountCondition()).as("correctCount")
        );
    }

    private Aggregation buildChapterAggregation(Long userId, List<Long> stageIds) {
        List<Integer> stageIdInts = stageIds.stream().map(Long::intValue).toList();
        return Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId).and("stageId").in(stageIdInts)),
                buildGroupOperation("userId")
        );
    }

    private GroupOperation buildGroupOperation(String field) {
        return Aggregation.group(field)
                .first("userId").as("userId")
                .count().as("totalCount")
                .sum(getCorrectCountCondition()).as("correctCount");
    }

    private ConditionalOperators.Cond getCorrectCountCondition() {
        return ConditionalOperators.when(
                ComparisonOperators.Eq.valueOf("isCorrect").equalToValue(true)
        ).then(1).otherwise(0);
    }

    private Document executeAggregation(Aggregation aggregation) {
        Document result = mongoTemplate.aggregate(aggregation, "user_progress_logs", Document.class)
                .getUniqueMappedResult();
        validateAggregationResult(result);
        return result;
    }

    // --- Validation ---

    private void validateAggregationResult(Document result) {
        if (result == null) {
            throw new GeneralException(ErrorStatus.USER_ACCURACY_NOT_FOUND);
        }
    }

    private void validateStageIds(List<Long> stageIds) {
        if (stageIds == null || stageIds.isEmpty()) {
            throw new GeneralException(ErrorStatus.USER_ACCURACY_NOT_FOUND);
        }
    }
}
