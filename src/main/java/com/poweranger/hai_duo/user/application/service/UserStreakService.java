package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.response.ApiResponse;
import com.poweranger.hai_duo.user.api.dto.UserStreakDto;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class UserStreakService {

    private final MongoTemplate mongoTemplate;

    public ApiResponse<UserStreakDto> getUserStreak(Long userId) {
        List<LocalDate> dates = fetchAnsweredDates(userId);
        int streak = calculateStreakCount(dates);
        return ApiResponse.onSuccess(UserStreakDto.from(userId, streak));
    }

    private List<LocalDate> fetchAnsweredDates(Long userId) {
        Aggregation aggregation = buildAnsweredDateAggregation(userId);
        return extractDatesFromAggregation(aggregation);
    }

    private Aggregation buildAnsweredDateAggregation(Long userId) {
        ProjectionOperation project = project()
                .andExpression("userId").as("userId")
                .andExpression("dateToString('%Y-%m-%d', answeredAt)").as("date");

        return newAggregation(
                match(Criteria.where("userId").is(userId)),
                project,
                group("date").first("date").as("date"),
                sort(Sort.Direction.DESC, "date")
        );
    }

    private List<LocalDate> extractDatesFromAggregation(Aggregation aggregation) {
        AggregationResults<Document> results =
                mongoTemplate.aggregate(aggregation, "user_progress_logs", Document.class);

        return results.getMappedResults().stream()
                .map(doc -> LocalDate.parse(doc.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .toList();
    }

    private int calculateStreakCount(List<LocalDate> dates) {
        if (dates.isEmpty()) {
            return 0;
        }
        if (!isToday(dates.get(0))) {
            return 0;
        }
        return countConsecutiveDates(dates);
    }

    private boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }

    private int countConsecutiveDates(List<LocalDate> dates) {
        if (dates.isEmpty()) {
            return 0;
        }

        int count = 1;

        for (int i = 1; i < dates.size(); i++) {
            LocalDate yesterday = dates.get(i - 1).minusDays(1);
            boolean isConsecutive = dates.get(i).equals(yesterday);
            if (!isConsecutive) return count;
            count++;
        }

        return count;
    }
}
