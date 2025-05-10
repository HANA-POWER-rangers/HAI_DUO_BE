package com.poweranger.hai_duo.user.domain.repository;

import com.poweranger.hai_duo.user.domain.entity.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<GameCharacter, Long> {
}
