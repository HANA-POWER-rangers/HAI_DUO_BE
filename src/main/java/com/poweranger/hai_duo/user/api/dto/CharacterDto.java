package com.poweranger.hai_duo.user.api.dto;

import com.poweranger.hai_duo.user.domain.entity.Character;

public record CharacterDto(Long characterId, String characterName) {
    public static CharacterDto from(Character character) {
        return new CharacterDto(character.getCharacterId(), character.getCharacterName());
    }
}
