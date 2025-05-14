package com.poweranger.hai_duo.learning.api.dto;

import com.poweranger.hai_duo.learning.domain.entity.GameCharacter;

public record CharacterDto(Long characterId, String characterName) {

    public static CharacterDto from(GameCharacter gameCharacter) {
        return new CharacterDto(gameCharacter.getCharacterId(), gameCharacter.getCharacterName());
    }
    
}
