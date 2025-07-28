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
import java.util.Optional;
import java.util.function.Function;

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

    public <T, R> R mapToDto(Optional<T> entityOpt, Function<T, R> mapper) {
        return entityOpt.map(mapper).orElse(null);
    }

    public QuizByStageNumberDto toQuizByStageNumberDto(
            Stage stage,
            QuizMeaningDto meaning,
            QuizCardDto card,
            QuizOXDto ox,
            QuizBlankDto blank
    ) {
        return QuizByStageNumberDto.builder()
                .stageId(stage.getStageId())
                .stageName(stage.getStageName())
                .stageNumber(stage.getStageNumber())
                .quizMeaning(meaning)
                .quizCard(card)
                .quizOX(ox)
                .quizBlank(blank)
                .build();
    }

    public QuizUnionDto getQuizByStageIdAndType(Long stageId, QuizType type) {
        return switch (type) {
            case MEAN -> mapToDto(quizReader.getMeaningQuizByStageId(stageId), this::toDto);
            case CARD -> mapToDto(quizReader.getCardQuizByStageId(stageId), this::toDto);
            case OX -> mapToDto(quizReader.getOXQuizByStageId(stageId), this::toDto);
            case BLANK -> mapToDto(quizReader.getBlankQuizByStageId(stageId), this::toDto);
        };
    }

    public QuizByStageNumberDto buildQuizDtoByStage(Stage stage) {
        return toQuizByStageNumberDto(
                stage,
                mapToDto(quizReader.getMeaningQuizByStage(stage), this::toDto),
                mapToDto(quizReader.getCardQuizByStage(stage), this::toDto),
                mapToDto(quizReader.getOXQuizByStage(stage), this::toDto),
                mapToDto(quizReader.getBlankQuizByStage(stage), this::toDto)
        );
    }

    public List<QuizByStageNumberDto> buildQuizDtoByStageList(List<Stage> stages) {
        return stages.stream()
                .map(this::buildQuizDtoByStage)
                .toList();
    }

    public QuizByChapterIdDto toQuizByChapterDto(
            Chapter chapter,
            List<QuizByStageNumberDto> quizzes
    ) {
        return QuizByChapterIdDto.builder()
                .chapterId(chapter.getChapterId())
                .quizzes(quizzes)
                .build();
    }

    public List<QuizUnionDto> buildQuizDtoListByChapterAndType(List<Stage> stages, QuizType type) {
        return stages.stream()
                .map(stage -> getQuizByStageIdAndType(stage.getStageId(), type))
                .filter(Objects::nonNull)
                .toList();
    }

    public QuizMeaningDto getMeaningQuizByStage(Stage stage) {
        return mapToDto(quizReader.getMeaningQuizByStage(stage), this::toDto);
    }

    public QuizCardDto getCardQuizByStage(Stage stage) {
        return mapToDto(quizReader.getCardQuizByStage(stage), this::toDto);
    }

    public QuizOXDto getOXQuizByStage(Stage stage) {
        return mapToDto(quizReader.getOXQuizByStage(stage), this::toDto);
    }

    public QuizBlankDto getBlankQuizByStage(Stage stage) {
        return mapToDto(quizReader.getBlankQuizByStage(stage), this::toDto);
    }
}
