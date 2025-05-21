package com.poweranger.hai_duo.progress.domain.repository;

import com.poweranger.hai_duo.progress.domain.entity.Chapter;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    @Query("SELECT s.stageId FROM Stage s WHERE s.chapter.chapterId = :chapterId")
    List<Long> findStageIdsByChapterId(@Param("chapterId") Long chapterId);

    List<Stage> findAllByChapter(Chapter chapter);
}
