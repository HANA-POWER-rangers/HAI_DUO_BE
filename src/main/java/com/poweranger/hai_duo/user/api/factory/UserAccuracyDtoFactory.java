package com.poweranger.hai_duo.user.api.factory;

import com.poweranger.hai_duo.user.api.dto.*;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class UserAccuracyDtoFactory {

    public UserAccuracyDto buildUserAccuracyDto(Long userId, Document result) {
        return new UserAccuracyDto(
                userId,
                getIntValue(result, "totalCount"),
                getIntValue(result, "correctCount"),
                getAccuracyRate(result)
        );
    }

    public UserAccuracyByStageDto buildUserAccuracyDtoByStage(Long userId, Long stageId, Document result) {
        return new UserAccuracyByStageDto(
                userId,
                stageId,
                getIntValue(result, "totalCount"),
                getIntValue(result, "correctCount"),
                getAccuracyRate(result)
        );
    }

    public UserAccuracyByChapterDto buildUserAccuracyDtoByChapter(Long userId, Long chapterId, Document result) {
        return new UserAccuracyByChapterDto(
                userId,
                chapterId,
                getIntValue(result, "totalCount"),
                getIntValue(result, "correctCount"),
                getAccuracyRate(result)
        );
    }

    public UserAccuracyByLevelDto buildUserAccuracyDtoByLevel(Long userId, Long levelId, Document result) {
        return new UserAccuracyByLevelDto(
                userId,
                levelId,
                getIntValue(result, "totalCount"),
                getIntValue(result, "correctCount"),
                getAccuracyRate(result)
        );
    }

    private int getIntValue(Document doc, String key) {
        if (doc == null || doc.get(key) == null) {
            return 0;
        }

        Object value = doc.get(key);
        return convertToInt(value);
    }

    private int convertToInt(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        return 0;
    }

    private float getAccuracyRate(Document doc) {
        int total = getIntValue(doc, "totalCount");
        int correct = getIntValue(doc, "correctCount");
        if (total == 0) return 0.0f;
        return Math.round(((float) correct / total) * 10000) / 100.0f;
    }
}
