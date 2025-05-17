package com.poweranger.hai_duo.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTempUserToken(String tempUserId);
}
