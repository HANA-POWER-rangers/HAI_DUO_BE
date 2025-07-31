package com.poweranger.hai_duo.quiz.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.progress.domain.repository.ChapterRepository;
import com.poweranger.hai_duo.progress.domain.repository.StageRepository;
import com.poweranger.hai_duo.quiz.api.dto.*;
import com.poweranger.hai_duo.quiz.api.factory.QuizDtoFactory;
import com.poweranger.hai_duo.quiz.domain.entity.QuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizInquiryService {

    private final StageRepository stageRepository;
    private final ChapterRepository chapterRepository;
    private final QuizDtoFactory quizDtoFactory;

    public List<QuizByStageNumberDto> quizzesByStageKey(Long chapterId, int stageNumber) {
        Stage stage = getStageByChapterIdAndStageNumber(chapterId, stageNumber);
        return List.of(toQuizByStageDto(stage));
    }

    public List<QuizUnionDto> quizByStageKeyAndType(Long chapterId, int stageNumber, QuizType quizType) {
        Stage stage = getStageByChapterIdAndStageNumber(chapterId, stageNumber);
        return quizDtoFactory.getQuizUnionDtosByStage(stage, quizType);
    }

    public QuizByChapterIdDto allQuizzesInChapter(Long chapterId) {
        Chapter chapter = getChapterById(chapterId);
        List<Stage> stages = stageRepository.findAllByChapter(chapter);

        List<QuizByStageNumberDto> quizList = stages.stream()
                .map(this::toQuizByStageDto)
                .toList();

        return new QuizByChapterIdDto(chapterId, quizList);
    }

    public List<QuizTypeGroupedByStageDto> quizzesInChapterByType(Long chapterId, QuizType quizType) {
        Chapter chapter = getChapterById(chapterId);
        List<Stage> stages = stageRepository.findAllByChapter(chapter);

        return stages.stream()
                .map(stage -> new QuizTypeGroupedByStageDto(
                        stage.getStageId(),
                        stage.getStageName(),
                        stage.getStageNumber(),
                        quizDtoFactory.getQuizUnionDtosByStage(stage, quizType)
                ))
                .toList();
    }

    private QuizByStageNumberDto toQuizByStageDto(Stage stage) {
        List<QuizSetDto> sets = quizDtoFactory.getQuizSetsByStage(stage);

        return new QuizByStageNumberDto(
                stage.getStageId(),
                stage.getStageName(),
                stage.getStageNumber(),
                sets.size(),
                sets
        );
    }

    private Stage getStageByChapterIdAndStageNumber(Long chapterId, int stageNumber) {
        return stageRepository.findByChapter_ChapterIdAndStageNumber(chapterId, stageNumber)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STAGE_NOT_FOUND));
    }

    private Chapter getChapterById(Long chapterId) {
        return chapterRepository.findById(chapterId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHAPTER_NOT_FOUND));
    }
}
