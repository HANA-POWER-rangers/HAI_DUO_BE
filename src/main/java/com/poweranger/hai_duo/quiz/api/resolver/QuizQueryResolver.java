package com.poweranger.hai_duo.quiz.api.resolver;

import com.poweranger.hai_duo.quiz.api.dto.*;
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

    @QueryMapping
    public List<QuizByStageNumberDto> quizzesByStageKey(
            @Argument Long chapterId,
            @Argument Integer stageNumber) {
        return quizInquiryService.quizzesByStageKey(chapterId, stageNumber);
    }

    @QueryMapping
    public QuizTypeGroupedByStageDto quizByStageKeyAndType(
            @Argument Long chapterId,
            @Argument Integer stageNumber,
            @Argument QuizType quizType) {
        return quizInquiryService.quizByStageKeyAndType(chapterId, stageNumber, quizType);
    }

    @QueryMapping
    public QuizByChapterIdDto allQuizzesInChapter(@Argument Long chapterId) {
        return quizInquiryService.allQuizzesInChapter(chapterId);
    }

    @QueryMapping
    public List<QuizTypeGroupedByStageDto> quizzesInChapterByType(
            @Argument Long chapterId,
            @Argument QuizType quizType) {
        return quizInquiryService.quizzesInChapterByType(chapterId, quizType);
    }
}
