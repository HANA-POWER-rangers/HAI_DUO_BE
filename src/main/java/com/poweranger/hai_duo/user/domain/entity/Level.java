package com.poweranger.hai_duo.user.domain.entity;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.user.domain.repository.LevelRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;

    @Column(nullable = false)
    private int requiredExp;

    public Optional<Level> findNextLevel(LevelRepository levelRepository) {
        return levelRepository.findById(this.levelId + 1);
    }

}
