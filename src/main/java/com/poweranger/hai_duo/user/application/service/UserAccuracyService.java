package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.response.ApiResponse;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccuracyService {

    private final MongoTemplate mongoTemplate;

    public ApiResponse<UserAccuracyDto> getUserAccuracy(Long userId) {
        Aggregation aggregation = buildAggregation(userId);
        Document result = executeAggregation(aggregation);
        return ApiResponse.onSuccess(mapToDto(userId, result));
    }

    private Aggregation buildAggregation(Long userId) {
        MatchOperation match = Aggregation.match(Criteria.where("userId").is(userId));
        GroupOperation group = Aggregation.group("userId")
                .count().as("totalCount")
                .sum(ConditionalOperators.when(Criteria.where("isCorrect").is(true)).then(1).otherwise(0)).as("correctCount");

        return Aggregation.newAggregation(match, group);
    }

    private Document executeAggregation(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, "user_progress_logs", Document.class)
                .getUniqueMappedResult();
    }

    private UserAccuracyDto mapToDto(Long userId, Document result) {
        if (result == null) {
            return new UserAccuracyDto(userId, 0, 0, 0.0f);
        }

        int total = result.getInteger("totalCount", 0);
        int correct = result.getInteger("correctCount", 0);
        float rate = total == 0 ? 0.0f : ((float) correct / total) * 100;

        return new UserAccuracyDto(userId, total, correct, rate);
    }
}
