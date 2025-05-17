package com.poweranger.hai_duo.quiz.api.resolver;

import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizPayloadDto;
import com.poweranger.hai_duo.quiz.application.service.QuizSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QuizMutationResolver {

    private final QuizSubmissionService quizSubmissionService;

    @MutationMapping
    public SubmitQuizPayloadDto submitQuiz(@Argument SubmitQuizInputDto input) {
        return quizSubmissionService.submitQuiz(input);
    }
}
