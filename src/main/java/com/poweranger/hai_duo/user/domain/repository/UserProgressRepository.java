package com.poweranger.hai_duo.user.domain.repository;

import com.poweranger.hai_duo.user.domain.entity.mongodb.UserProgressLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserProgressRepository extends MongoRepository<UserProgressLog, Long> {

    List<UserProgressLog> findByUserId(Long userId);
}
