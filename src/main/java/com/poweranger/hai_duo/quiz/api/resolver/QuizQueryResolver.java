package com.poweranger.hai_duo.quiz.api.resolver;

import com.poweranger.hai_duo.quiz.api.dto.QuizByChapterIdDto;
import com.poweranger.hai_duo.quiz.api.dto.QuizByStageIdDto;
import com.poweranger.hai_duo.quiz.api.factory.QuizDtoFactory;
import com.poweranger.hai_duo.quiz.application.service.QuizInquiryService;
import com.poweranger.hai_duo.quiz.domain.entity.QuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuizQueryResolver {

    private final QuizInquiryService quizInquiryService;
    private final QuizDtoFactory quizDtoFactory;

    @QueryMapping
    public QuizByStageIdDto getQuizzesByStageId(@Argument Long stageId) {
        return quizInquiryService.getQuizzesByStageId(stageId);
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

    @QueryMapping
    public QuizByChapterIdDto getQuizzesByChapterId(@Argument Long chapterId){
        return quizInquiryService.getQuizzesByChapterId(chapterId);
    }

    @QueryMapping
    public List<Object> getQuizzesByChapterIdAndType(
            @Argument Long chapterId,
            @Argument QuizType quizType
    ) {
        return quizInquiryService.getQuizzesByChapterIdAndType(chapterId, quizType);
    }

}
