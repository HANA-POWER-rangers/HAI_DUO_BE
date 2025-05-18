package com.poweranger.hai_duo.quiz.application.service;

import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.progress.domain.repository.StageRepository;
import com.poweranger.hai_duo.progress.domain.repository.ChapterRepository;
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

    public QuizByStageIdDto getQuizzesByStageId(Long stageId) {
        Stage stage = getStageByStageId(stageId);
        return quizDtoFactory.buildQuizDtoByStage(stage);
    }

    public QuizByChapterIdDto getQuizzesByChapterId(Long chapterId) {
        Chapter chapter = getChapterByChapterId(chapterId);
        List<Stage> stages = stageRepository.findAllByChapter(chapter);
        List<QuizByStageIdDto> quizzes = quizDtoFactory.buildQuizDtoByStageList(stages);
        return quizDtoFactory.toQuizByChapterDto(chapter, quizzes);
    }

    private Stage getStageByStageId(Long stageId) {
        return stageRepository.findById(stageId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STAGE_NOT_FOUND));
    }

    private Chapter getChapterByChapterId(Long chapterId) {
        return chapterRepository.findById(chapterId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHAPTER_NOT_FOUND));
    }

    public List<Object> getQuizzesByChapterIdAndType(Long chapterId, QuizType quizType) {
        Chapter chapter = getChapterByChapterId(chapterId);
        List<Stage> stages = stageRepository.findAllByChapter(chapter);
        return quizDtoFactory.buildQuizDtoListByChapterAndType(stages, quizType);
    }

}
