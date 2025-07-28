package com.poweranger.hai_duo.quiz.api.factory;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.api.dto.*;
import com.poweranger.hai_duo.quiz.application.reader.QuizReader;
import com.poweranger.hai_duo.quiz.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizDtoFactory {

    private final QuizReader quizReader;

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

    public List<QuizUnionDto> getQuizUnionDtosByStage(Stage stage, QuizType quizType) {
        return switch (quizType) {
            case MEAN -> quizReader.getMeaningQuizzesByStage(stage).stream()
                    .map(q -> (QuizUnionDto) toDto(q))
                    .toList();
            case CARD -> quizReader.getCardQuizzesByStage(stage).stream()
                    .map(q -> (QuizUnionDto) toDto(q))
                    .toList();
            case OX -> quizReader.getOXQuizzesByStage(stage).stream()
                    .map(q -> (QuizUnionDto) toDto(q))
                    .toList();
            case BLANK -> quizReader.getBlankQuizzesByStage(stage).stream()
                    .map(q -> (QuizUnionDto) toDto(q))
                    .toList();
        };
    }

    public List<QuizSetDto> getQuizSetsByStage(Stage stage) {
        List<QuizMeaning> meanings = quizReader.getMeaningQuizzesByStage(stage);
        List<QuizCard> cards = quizReader.getCardQuizzesByStage(stage);
        List<QuizOX> oxes = quizReader.getOXQuizzesByStage(stage);
        List<QuizBlank> blanks = quizReader.getBlankQuizzesByStage(stage);

        int setSize = Math.min(
                Math.min(meanings.size(), cards.size()),
                Math.min(oxes.size(), blanks.size())
        );

        List<QuizSetDto> sets = new ArrayList<>();
        for (int i = 0; i < setSize; i++) {
            sets.add(new QuizSetDto(
                    toDto(meanings.get(i)),
                    toDto(cards.get(i)),
                    toDto(oxes.get(i)),
                    toDto(blanks.get(i))
            ));
        }
        return sets;
    }
}
