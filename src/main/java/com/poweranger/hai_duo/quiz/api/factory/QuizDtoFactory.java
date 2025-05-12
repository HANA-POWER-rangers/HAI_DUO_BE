package com.poweranger.hai_duo.quiz.api.factory;

import com.poweranger.hai_duo.learning.domain.entity.Chapter;
import com.poweranger.hai_duo.learning.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.api.dto.*;
import com.poweranger.hai_duo.quiz.application.reader.QuizReader;
import com.poweranger.hai_duo.quiz.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
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

    public QuizByStageIdDto toQuizByStageDto(Stage stage,
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

    public QuizMeaningDto getMeaningQuizByStageId(Long stageId) {
        return getQuizDto(stageId, quizReader::getMeaningQuizByStageId, this::toDto);
    }

    public QuizCardDto getCardQuizByStageId(Long stageId) {
        return getQuizDto(stageId, quizReader::getCardQuizByStageId, this::toDto);
    }

    public QuizOXDto getOXQuizByStageId(Long stageId) {
        return getQuizDto(stageId, quizReader::getOXQuizByStageId, this::toDto);
    }

    public QuizBlankDto getBlankQuizByStageId(Long stageId) {
        return getQuizDto(stageId, quizReader::getBlankQuizByStageId, this::toDto);
    }

    private <T, R> R getQuizDto(Long stageId, Function<Long, Optional<T>> reader, Function<T, R> mapper) {
        return reader.apply(stageId)
                .map(mapper)
                .orElse(null);
    }

    public QuizByStageIdDto buildQuizDtoByStage(Stage stage) {
        return toQuizByStageDto(
                stage,
                mapToDto(quizReader.getMeaningQuizByStage(stage), this::toDto),
                mapToDto(quizReader.getCardQuizByStage(stage), this::toDto),
                mapToDto(quizReader.getOXQuizByStage(stage), this::toDto),
                mapToDto(quizReader.getBlankQuizByStage(stage), this::toDto)
        );
    }

    public List<QuizByStageIdDto> buildQuizDtoByStageList(List<Stage> stages) {
        return stages.stream()
                .map(this::buildQuizDtoByStage)
                .toList();
    }

    public QuizByChapterIdDto toQuizByChapterDto(
            Chapter chapter,
            List<QuizByStageIdDto> quizzes) {
        return QuizByChapterIdDto.builder()
                .chapterId(chapter.getChapterId())
                .quizzes(quizzes)
                .build();
    }

    public List<Object> buildQuizDtoListByChapterAndType(List<Stage> stages, QuizType quizType) {
        return stages.stream()
                .map(stage -> (Object) switch (quizType) {
                    case MEAN -> getMeaningQuizByStageId(stage.getStageId());
                    case CARD -> getCardQuizByStageId(stage.getStageId());
                    case OX -> getOXQuizByStageId(stage.getStageId());
                    case BLANK -> getBlankQuizByStageId(stage.getStageId());
                })
                .toList();
    }

}
