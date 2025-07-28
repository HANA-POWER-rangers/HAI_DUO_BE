package com.poweranger.hai_duo.quiz.application.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.progress.domain.repository.StageRepository;
import com.poweranger.hai_duo.quiz.domain.entity.*;
import com.poweranger.hai_duo.quiz.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizBulkImporter {

    private final StageRepository stageRepository;
    private final QuizCardRepository quizCardRepository;
    private final QuizBlankRepository quizBlankRepository;
    private final QuizMeaningRepository quizMeaningRepository;
    private final QuizOXRepository quizOXRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void importAllQuizzes(Path basePath) throws IOException {
        try (DirectoryStream<Path> chapterDirs = Files.newDirectoryStream(basePath)) {
            for (Path chapterPath : chapterDirs) {
                if (!Files.isDirectory(chapterPath)) continue;

                Long chapterId = Long.parseLong(chapterPath.getFileName().toString());

                try (DirectoryStream<Path> stageDirs = Files.newDirectoryStream(chapterPath)) {
                    for (Path stagePath : stageDirs) {
                        if (!Files.isDirectory(stagePath)) continue;

                        String stageDirName = stagePath.getFileName().toString();
                        Integer stageNumber = parseStageNumber(stageDirName);

                        System.out.println("ChapterId = " + chapterId + ", StageDirName = " + stageDirName + ", StageNumber = " + stageNumber);
                        Stage stage = stageRepository.findByChapter_ChapterIdAndStageNumber(chapterId, stageNumber)
                                .orElseThrow(() -> new GeneralException(ErrorStatus.STAGE_NOT_FOUND));

                        insertAllQuizTypes(stagePath, stage);
                    }
                }
            }
        }
    }

    private Integer parseStageNumber(String dirName) {
        String numberPart = dirName.split("-")[0].trim();
        return Integer.parseInt(numberPart);
    }

    private void insertAllQuizTypes(Path stageDir, Stage stage) throws IOException {
        insertOX(stageDir.resolve("quiz_ox.json"), stage);
        insertCard(stageDir.resolve("quiz_card.json"), stage);
        insertBlank(stageDir.resolve("quiz_blank.json"), stage);
        insertMeaning(stageDir.resolve("quiz_meaning.json"), stage);
    }

    private void insertOX(Path path, Stage stage) throws IOException {
        if (!Files.exists(path)) return;
        List<Map<String, Object>> data = objectMapper.readValue(path.toFile(), List.class);
        for (Map<String, Object> item : data) {
            QuizOX q = new QuizOX();
            q.setStage(stage);
            q.setWord((String) item.get("word"));
            q.setMeaning((String) item.get("meaning"));
            q.setCorrect((Boolean) item.get("is_correct"));
            quizOXRepository.save(q);
        }
    }

    private void insertCard(Path path, Stage stage) throws IOException {
        if (!Files.exists(path)) return;
        List<Map<String, Object>> data = objectMapper.readValue(path.toFile(), List.class);
        for (Map<String, Object> item : data) {
            QuizCard q = new QuizCard();
            q.setStage(stage);
            q.setMeaning((String) item.get("meaning"));
            q.setChoices(objectMapper.writeValueAsString(item.get("choices")));
            q.setCorrectWord((String) item.get("correct_word"));
            quizCardRepository.save(q);
        }
    }

    private void insertBlank(Path path, Stage stage) throws IOException {
        if (!Files.exists(path)) return;
        List<Map<String, Object>> data = objectMapper.readValue(path.toFile(), List.class);
        for (Map<String, Object> item : data) {
            QuizBlank q = new QuizBlank();
            q.setStage(stage);
            q.setSentenceWithBlank((String) item.get("sentence_with_blank"));
            q.setCorrectWord((String) item.get("correct_word"));
            quizBlankRepository.save(q);
        }
    }

    private void insertMeaning(Path path, Stage stage) throws IOException {
        if (!Files.exists(path)) return;
        List<Map<String, Object>> data = objectMapper.readValue(path.toFile(), List.class);
        for (Map<String, Object> item : data) {
            QuizMeaning q = new QuizMeaning();
            q.setStage(stage);
            q.setWord((String) item.get("word"));
            q.setMeaning((String) item.get("meaning"));
            q.setExampleSentence((String) item.get("example_sentence"));
            quizMeaningRepository.save(q);
        }
    }
}
