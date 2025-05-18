package com.poweranger.hai_duo.user.api.factory;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyByChapterDto;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyByLevelDto;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyByStageDto;
import com.poweranger.hai_duo.user.api.dto.UserAccuracyDto;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class UserAccuracyDtoFactory {

    public UserAccuracyDto buildUserAccuracyDto(Document result) {
        return new UserAccuracyDto(
                convertToLong(result.get("userId")),
                convertToInt(result.get("totalCount")),
                convertToInt(result.get("correctCount")),
                calculateAccuracyRate(result)
        );
    }

    public UserAccuracyByStageDto buildUserAccuracyDtoByStage(Document result) {
        return new UserAccuracyByStageDto(
                convertToLong(result.get("userId")),
                convertToLong(result.get("stageId")),
                convertToInt(result.get("totalCount")),
                convertToInt(result.get("correctCount")),
                calculateAccuracyRate(result)
        );
    }

    public UserAccuracyByChapterDto buildUserAccuracyDtoByChapter(Document result, Long chapterId) {
        return new UserAccuracyByChapterDto(
                convertToLong(result.get("userId")),
                chapterId,
                convertToInt(result.get("totalCount")),
                convertToInt(result.get("correctCount")),
                calculateAccuracyRate(result)
        );
    }

    public UserAccuracyByLevelDto buildUserAccuracyDtoByLevel(Document result, Long levelId) {
        return new UserAccuracyByLevelDto(
                convertToLong(result.get("userId")),
                levelId,
                convertToInt(result.get("totalCount")),
                convertToInt(result.get("correctCount")),
                calculateAccuracyRate(result)
        );
    }

    private Long convertToLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        throw new GeneralException(ErrorStatus.USER_ACCURACY_NOT_FOUND);
    }

    private int convertToInt(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        return 0;
    }

    private float calculateAccuracyRate(Document result) {
        int total = convertToInt(result.get("totalCount"));
        int correct = convertToInt(result.get("correctCount"));
        if (total == 0) return 0.0f;
        return Math.round(((float) correct / total) * 10000) / 100.0f;
    }

}
