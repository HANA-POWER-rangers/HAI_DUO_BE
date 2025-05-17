package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class QuizLogRepository {

    private final MongoTemplate mongoTemplate;

    public void save(SubmitQuizInputDto input) {
        Map<String, Object> doc = new HashMap<>();
        doc.put("userId", input.userId());
        doc.put("stageId", input.stageId());
        doc.put("quizType", input.quizType());
        doc.put("selectedOption", input.selectedOption());
        doc.put("elapsedTime", input.elapsedTime());
        doc.put("submittedAt", Instant.now());

        mongoTemplate.save(doc, "user_quiz_logs");
    }
}
