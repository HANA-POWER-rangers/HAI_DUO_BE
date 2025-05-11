package com.poweranger.hai_duo.quiz.api.resolver;

import com.poweranger.hai_duo.quiz.api.dto.QuizByStageIdDto;
import com.poweranger.hai_duo.quiz.application.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QuizQueryResolver {

    private final QuizService quizService;

    @QueryMapping
    public QuizByStageIdDto getQuizzes(@Argument Long stageId) {
        return quizService.getQuizzes(stageId);
    }

}
