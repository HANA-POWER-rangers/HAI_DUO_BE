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
        Document result = fetchOverallAggregationResult(userId);
        validateAggregationResult(result);
        return buildUserAccuracyDto(result);
    }

    public UserAccuracyByStageDto getUserAccuracyByStage(Long userId, Long stageId) {
        Document result = fetchStageAggregationResult(userId, stageId);
        validateAggregationResult(result);
        return buildUserAccuracyDtoByStage(result);
    }

    public UserAccuracyByChapterDto getUserAccuracyByChapter(Long userId, Long chapterId) {
        Document result = fetchChapterAggregationResult(userId, chapterId);
        validateAggregationResult(result);
        return buildUserAccuracyDtoByChapter(result, chapterId);
    }

    private Document fetchOverallAggregationResult(Long userId) {
        MatchOperation match = Aggregation.match(Criteria.where("userId").is(userId));

        GroupOperation group = Aggregation.group("userId")
                .count().as("totalCount")
                .sum(ConditionalOperators.when(
                        ComparisonOperators.Eq.valueOf("isCorrect").equalToValue(true)
                ).then(1).otherwise(0)).as("correctCount");

        Aggregation aggregation = Aggregation.newAggregation(match, group);
        return mongoTemplate.aggregate(aggregation, "user_progress_logs", Document.class)
                .getUniqueMappedResult();
    }

    private Document fetchStageAggregationResult(Long userId, Long stageId) {
        MatchOperation match = Aggregation.match(
                Criteria.where("userId").is(userId).and("stageId").is(stageId)
        );

        GroupOperation group = Aggregation.group("stageId")
                .first("userId").as("userId")
                .first("stageId").as("stageId")
                .count().as("totalCount")
                .sum(ConditionalOperators.when(
                        ComparisonOperators.Eq.valueOf("isCorrect").equalToValue(true)
                ).then(1).otherwise(0)).as("correctCount");

        Aggregation aggregation = Aggregation.newAggregation(match, group);
        return mongoTemplate.aggregate(aggregation, "user_progress_logs", Document.class)
                .getUniqueMappedResult();
    }

    private Document fetchChapterAggregationResult(Long userId, Long chapterId) {
        List<Long> stageIds = stageRepository.findStageIdsByChapterId(chapterId);

        if (stageIds.isEmpty()) {
            throw new GeneralException(ErrorStatus.USER_ACCURACY_NOT_FOUND);
        }

        MatchOperation match = Aggregation.match(
                Criteria.where("userId").is(userId).and("stageId").in(stageIds)
        );

        GroupOperation group = Aggregation.group("userId")
                .first("userId").as("userId")
                .count().as("totalCount")
                .sum(ConditionalOperators.when(
                        ComparisonOperators.Eq.valueOf("isCorrect").equalToValue(true)
                ).then(1).otherwise(0)).as("correctCount");

        Aggregation aggregation = Aggregation.newAggregation(match, group);
        return mongoTemplate.aggregate(aggregation, "user_progress_logs", Document.class)
                .getUniqueMappedResult();
    }

    private void validateAggregationResult(Document result) {
        if (result == null) {
            throw new GeneralException(ErrorStatus.USER_ACCURACY_NOT_FOUND);
        }
    }

    private UserAccuracyDto buildUserAccuracyDto(Document result) {
        Long userId = convertToLong(result.get("_id"));
        int total = convertToInt(result.get("totalCount"));
        int correct = convertToInt(result.get("correctCount"));
        float accuracyRate = calculateAccuracyRate(total, correct);

        return new UserAccuracyDto(userId, total, correct, accuracyRate);
    }

    private UserAccuracyByStageDto buildUserAccuracyDtoByStage(Document result) {
        Long userId = convertToLong(result.get("userId"));
        Long stageId = convertToLong(result.get("stageId"));
        int total = convertToInt(result.get("totalCount"));
        int correct = convertToInt(result.get("correctCount"));
        float accuracyRate = calculateAccuracyRate(total, correct);

        return new UserAccuracyByStageDto(userId, stageId,total, correct, accuracyRate);
    }

    private UserAccuracyByChapterDto buildUserAccuracyDtoByChapter(Document result, Long chapterId) {
        Long userId = convertToLong(result.get("userId"));
        int total = convertToInt(result.get("totalCount"));
        int correct = convertToInt(result.get("correctCount"));
        float accuracyRate = calculateAccuracyRate(total, correct);

        return new UserAccuracyByChapterDto(userId, chapterId, total, correct, accuracyRate);
    }

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

    private float calculateAccuracyRate(int total, int correct) {
        if (total == 0) {
            return 0.0f;
        }
        float rawRate = ((float) correct / total) * 100;
        return Math.round(rawRate * 100) / 100.0f;
    }

}
