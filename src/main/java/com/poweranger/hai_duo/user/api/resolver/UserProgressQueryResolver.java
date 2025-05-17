package com.poweranger.hai_duo.user.api.resolver;

import com.poweranger.hai_duo.user.domain.entity.mongodb.UserProgressLog;
import com.poweranger.hai_duo.user.domain.repository.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserProgressQueryResolver {

    private final UserProgressRepository userProgressRepository;

    @QueryMapping
    public List<UserProgressLog> getUserQuizLogs(@Argument Long userId) {
        return userProgressRepository.findByUserId(userId);
    }

}
