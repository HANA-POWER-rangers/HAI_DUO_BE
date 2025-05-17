package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.ApiResponse;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
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
        AggregationResults<Document> results = executeAggregation(userId);
        Document result = results.getUniqueMappedResult();

        if (result == null || result.get("_id") == null) {
            throw new GeneralException(ErrorStatus.USER_ACCURACY_NOT_FOUND);
        }

        return convertToDto(result);
    }

    private AggregationResults<Document> executeAggregation(Long userId) {
        MatchOperation match = Aggregation.match(Criteria.where("userId").is(userId));

        GroupOperation group = Aggregation.group("userId")
                .count().as("totalCount")
                .sum(ConditionalOperators.when(
                        ComparisonOperators.Eq.valueOf("isCorrect").equalToValue(true)
                ).then(1).otherwise(0)).as("correctCount");

        Aggregation aggregation = Aggregation.newAggregation(match, group);
        return mongoTemplate.aggregate(aggregation, "user_progress_logs", Document.class);
    }

    private UserAccuracyDto convertToDto(Document result) {
        Long userId = ((Number) result.get("_id")).longValue();
        int total = ((Number) result.get("totalCount")).intValue();
        int correct = ((Number) result.get("correctCount")).intValue();
        float accuracyRate = total > 0 ? (correct / (float) total) * 100 : 0.0f;

        return new UserAccuracyDto(userId, total, correct, accuracyRate);
    }
}
