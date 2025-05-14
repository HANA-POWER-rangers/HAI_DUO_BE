package com.poweranger.hai_duo.learning.domain.repository;

import com.poweranger.hai_duo.learning.domain.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {

    @Query("SELECT u.level FROM User u WHERE u.userId = :userId")
    Optional<Level> findLevelByUserId(@Param("userId") Long userId);

}
