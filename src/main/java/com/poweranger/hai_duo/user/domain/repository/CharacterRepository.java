package com.poweranger.hai_duo.user.domain.repository;

import com.poweranger.hai_duo.user.domain.entity.mysql.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<GameCharacter, Long> {

    @Query("SELECT u.gameCharacter FROM User u WHERE u.userId = :userId")
    Optional<GameCharacter> findGameCharacterByUserId(@Param("userId") Long userId);

}
