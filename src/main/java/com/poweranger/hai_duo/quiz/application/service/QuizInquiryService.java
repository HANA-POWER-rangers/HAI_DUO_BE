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
        return List.of(quizDtoFactory.buildQuizDtoByStage(stage));
    }

    public List<QuizUnionDto> quizByStageKeyAndType(Long chapterId, int stageNumber, QuizType quizType) {
        Stage stage = getStageByChapterIdAndStageNumber(chapterId, stageNumber);
        return switch (quizType) {
            case MEAN -> quizDtoFactory.getMeaningQuizByStage(stage);
            case CARD -> quizDtoFactory.getCardQuizByStage(stage);
            case OX -> quizDtoFactory.getOXQuizByStage(stage);
            case BLANK -> quizDtoFactory.getBlankQuizByStage(stage);
        };
    }

    public QuizByChapterIdDto allQuizzesInChapter(Long chapterId) {
        Chapter chapter = getChapterById(chapterId);
        List<Stage> stages = stageRepository.findAllByChapter(chapter);
        List<QuizByStageNumberDto> quizList = quizDtoFactory.buildQuizDtoByStageList(stages);
        return quizDtoFactory.toQuizByChapterDto(chapter, quizList);
    }

    public List<QuizUnionDto> quizzesInChapterByType(Long chapterId, QuizType quizType) {
        Chapter chapter = getChapterById(chapterId);
        List<Stage> stages = stageRepository.findAllByChapter(chapter);
        return quizDtoFactory.buildQuizDtoListByChapterAndType(stages, quizType);
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
