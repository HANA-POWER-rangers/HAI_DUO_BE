package com.poweranger.hai_duo.quiz.application.service;

import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizPayloadDto;
import com.poweranger.hai_duo.quiz.domain.evaluator.QuizEvaluator;
import com.poweranger.hai_duo.quiz.domain.repository.QuizLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QuizSubmissionService {

    private final QuizEvaluator quizEvaluator;
    private final QuizLogRepository quizLogRepository;

    public SubmitQuizPayloadDto submitQuiz(SubmitQuizInputDto input) {
        boolean isCorrect = quizEvaluator.evaluate(input);
        String correctAnswer = quizEvaluator.getCorrectAnswer(input.stageId(), input.quizType());

        quizLogRepository.save(input, isCorrect, correctAnswer);

        return SubmitQuizPayloadDto.of(isCorrect, correctAnswer);
    }
}
