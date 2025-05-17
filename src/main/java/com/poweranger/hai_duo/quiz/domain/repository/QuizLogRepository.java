package com.poweranger.hai_duo.quiz.domain.repository;

import com.poweranger.hai_duo.user.domain.entity.mongodb.UserProgressLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizLogRepository {

    private final MongoTemplate mongoTemplate;

    public void save(UserProgressLog log) {
        mongoTemplate.save(log);
    }

}
