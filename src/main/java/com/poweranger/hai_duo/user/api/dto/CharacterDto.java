package com.poweranger.hai_duo.user.api.dto;

import com.poweranger.hai_duo.user.domain.entity.mysql.GameCharacter;

public record CharacterDto(Long characterId, String characterName) {

    public static CharacterDto from(GameCharacter gameCharacter) {
        return new CharacterDto(gameCharacter.getCharacterId(), gameCharacter.getCharacterName());
    }
    
}
