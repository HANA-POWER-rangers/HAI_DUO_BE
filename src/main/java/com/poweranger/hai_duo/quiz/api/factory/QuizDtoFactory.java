package com.poweranger.hai_duo.quiz.api.factory;

import com.poweranger.hai_duo.learning.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.api.dto.*;
import com.poweranger.hai_duo.quiz.domain.entity.QuizBlank;
import com.poweranger.hai_duo.quiz.domain.entity.QuizCard;
import com.poweranger.hai_duo.quiz.domain.entity.QuizMeaning;
import com.poweranger.hai_duo.quiz.domain.entity.QuizOX;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class QuizDtoFactory {

    public QuizMeaningDto toDto(QuizMeaning q) {
        return new QuizMeaningDto(q.getWord(), q.getMeaning(), q.getExampleSentence());
    }

    public QuizCardDto toDto(QuizCard q) {
        return new QuizCardDto(q.getMeaning(), q.getChoices(), q.getCorrectWord());
    }

    public QuizOXDto toDto(QuizOX q) {
        return new QuizOXDto(q.getWord(), q.getMeaning(), q.isCorrect());
    }

    public QuizBlankDto toDto(QuizBlank q) {
        return new QuizBlankDto(q.getSentenceWithBlank(), q.getCorrectWord());
    }

    public <T, R> R mapToDto(Optional<T> entityOpt, Function<T, R> mapper) {
        return entityOpt.map(mapper).orElse(null);
    }

    public QuizByStageIdDto from(Stage stage,
                                 QuizMeaningDto meaning,
                                 QuizCardDto card,
                                 QuizOXDto ox,
                                 QuizBlankDto blank) {
        return QuizByStageIdDto.builder()
                .stageId(stage.getStageId())
                .stageName(stage.getStageName())
                .quizMeaning(meaning)
                .quizCard(card)
                .quizOX(ox)
                .quizBlank(blank)
                .build();
    }
}
