package com.poweranger.hai_duo.quiz.api.resolver;

import com.poweranger.hai_duo.quiz.api.dto.QuizByStageIdDto;
import com.poweranger.hai_duo.quiz.api.factory.QuizDtoFactory;
import com.poweranger.hai_duo.quiz.application.service.QuizService;
import com.poweranger.hai_duo.quiz.domain.entity.QuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QuizQueryResolver {

    private final QuizService quizService;
    private final QuizDtoFactory quizDtoFactory;

    @QueryMapping
    public QuizByStageIdDto getQuizzesByStageId(@Argument Long stageId) {
        return quizService.getQuizzesByStageId(stageId);
    }

    @QueryMapping
    public Object getQuizByStageIdAndType(@Argument Long stageId, @Argument QuizType quizType) {
        return switch (quizType) {
            case MEAN -> quizDtoFactory.getMeaningQuizByStageId(stageId);
            case CARD -> quizDtoFactory.getCardQuizByStageId(stageId);
            case OX -> quizDtoFactory.getOXQuizByStageId(stageId);
            case BLANK -> quizDtoFactory.getBlankQuizByStageId(stageId);
        };
    }

}
