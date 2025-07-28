package com.poweranger.hai_duo.quiz.api.factory;

import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.api.dto.*;
import com.poweranger.hai_duo.quiz.application.reader.QuizReader;
import com.poweranger.hai_duo.quiz.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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

    public QuizByStageNumberDto buildQuizDtoByStage(Stage stage) {
        return new QuizByStageNumberDto(
                stage.getStageId(),
                stage.getStageName(),
                stage.getStageNumber(),
                quizReader.getMeaningQuizzesByStage(stage).stream().map(this::toDto).toList(),
                quizReader.getCardQuizzesByStage(stage).stream().map(this::toDto).toList(),
                quizReader.getOXQuizzesByStage(stage).stream().map(this::toDto).toList(),
                quizReader.getBlankQuizzesByStage(stage).stream().map(this::toDto).toList()
        );
    }

    public List<QuizByStageNumberDto> buildQuizDtoByStageList(List<Stage> stages) {
        return stages.stream()
                .map(this::buildQuizDtoByStage)
                .toList();
    }

    public QuizByChapterIdDto toQuizByChapterDto(Chapter chapter, List<QuizByStageNumberDto> quizzes) {
        return new QuizByChapterIdDto(chapter.getChapterId(), quizzes);
    }

    public List<QuizUnionDto> getMeaningQuizByStage(Stage stage) {
        return quizReader.getMeaningQuizzesByStage(stage).stream()
                .map((QuizMeaning q) -> (QuizUnionDto) toDto(q))
                .toList();
    }

    public List<QuizUnionDto> getCardQuizByStage(Stage stage) {
        return quizReader.getCardQuizzesByStage(stage).stream()
                .map((QuizCard q) -> (QuizUnionDto) toDto(q))
                .toList();
    }

    public List<QuizUnionDto> getOXQuizByStage(Stage stage) {
        return quizReader.getOXQuizzesByStage(stage).stream()
                .map((QuizOX q) -> (QuizUnionDto) toDto(q))
                .toList();
    }

    public List<QuizUnionDto> getBlankQuizByStage(Stage stage) {
        return quizReader.getBlankQuizzesByStage(stage).stream()
                .map((QuizBlank q) -> (QuizUnionDto) toDto(q))
                .toList();
    }

    public List<QuizUnionDto> buildQuizDtoListByChapterAndType(List<Stage> stages, QuizType quizType) {
        return stages.stream()
                .flatMap(stage -> switch (quizType) {
                    case MEAN -> getMeaningQuizByStage(stage).stream();
                    case CARD -> getCardQuizByStage(stage).stream();
                    case OX -> getOXQuizByStage(stage).stream();
                    case BLANK -> getBlankQuizByStage(stage).stream();
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
