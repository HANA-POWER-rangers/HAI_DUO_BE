package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.domain.repository.StageRepository;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyByChapterDto;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyByStageDto;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyDto;
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

    public UserAccuracyDto getUserAccuracy(Long userId) {
        Aggregation aggregation = buildUserAggregation(userId);
        Document result = executeAggregation(aggregation);
        return buildUserAccuracyDto(result);
    }

    public UserAccuracyByStageDto getUserAccuracyByStage(Long userId, Long stageId) {
        Aggregation aggregation = buildStageAggregation(userId, stageId);
        Document result = executeAggregation(aggregation);
        return buildUserAccuracyDtoByStage(result);
    }

    public UserAccuracyByChapterDto getUserAccuracyByChapter(Long userId, Long chapterId) {
        List<Long> stageIds = stageRepository.findStageIdsByChapterId(chapterId);
        validateStageIds(stageIds);

        Aggregation aggregation = buildChapterAggregation(userId, stageIds);
        Document result = executeAggregation(aggregation);
        return buildUserAccuracyDtoByChapter(result, chapterId);
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

    // --- DTO Builders ---

    private UserAccuracyDto buildUserAccuracyDto(Document result) {
        return new UserAccuracyDto(
                convertToLong(result.get("userId")),
                convertToInt(result.get("totalCount")),
                convertToInt(result.get("correctCount")),
                calculateAccuracyRate(result)
        );
    }

    private UserAccuracyByStageDto buildUserAccuracyDtoByStage(Document result) {
        return new UserAccuracyByStageDto(
                convertToLong(result.get("userId")),
                convertToLong(result.get("stageId")),
                convertToInt(result.get("totalCount")),
                convertToInt(result.get("correctCount")),
                calculateAccuracyRate(result)
        );
    }

    private UserAccuracyByChapterDto buildUserAccuracyDtoByChapter(Document result, Long chapterId) {
        return new UserAccuracyByChapterDto(
                convertToLong(result.get("userId")),
                chapterId,
                convertToInt(result.get("totalCount")),
                convertToInt(result.get("correctCount")),
                calculateAccuracyRate(result)
        );
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

    // --- Converter / Calculator ---

    private Long convertToLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        throw new GeneralException(ErrorStatus.USER_ACCURACY_NOT_FOUND);
    }

    private int convertToInt(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        return 0;
    }

    private float calculateAccuracyRate(Document result) {
        int total = convertToInt(result.get("totalCount"));
        int correct = convertToInt(result.get("correctCount"));
        if (total == 0) return 0.0f;
        return Math.round(((float) correct / total) * 10000) / 100.0f;
    }
}
