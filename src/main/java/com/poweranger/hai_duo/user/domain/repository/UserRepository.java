package com.poweranger.hai_duo.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByTempUserToken(String tempUserId);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.level " +
            "JOIN FETCH u.gameCharacter " +
            "WHERE u.userId = :userId")
    Optional<User> findByIdWithLevelAndCharacter(@Param("userId") Long userId);
}
