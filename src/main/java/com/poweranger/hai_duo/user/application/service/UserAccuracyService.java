package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyByStageDto;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyDto;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccuracyService {

    private final MongoTemplate mongoTemplate;

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

    private void validateAggregationResult(Document result) {
        if (result == null || result.get("_id") == null) {
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
