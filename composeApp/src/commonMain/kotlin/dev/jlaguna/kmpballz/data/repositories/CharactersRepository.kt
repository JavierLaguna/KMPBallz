package dev.jlaguna.kmpballz.data.repositories

import dev.jlaguna.kmpballz.data.models.Character
import dev.jlaguna.kmpballz.data.remote.CharacterService

class CharactersRepository(
    private val characterService: CharacterService
) {

    suspend fun fetchCharacterById(id: Int): Character {
        val response = characterService.fetchCharacterById(id)
        return Character(
            response.id,
            response.name,
            response.description
        )
    }
}