package dev.jlaguna.kmpballz.data.repositories

import dev.jlaguna.kmpballz.data.models.Character
import dev.jlaguna.kmpballz.data.remote.CharacterService
import dev.jlaguna.kmpballz.data.remote.responses.CharacterResponse

class CharactersRepository(
    private val characterService: CharacterService
) {

    suspend fun fetchCharacters(): List<Character> {
        val response = characterService.fetchCharacters()
        return response.items.mapNotNull { it.toCharacter() }
    }

    suspend fun fetchCharacterById(id: Int): Character {
        val character = characterService.fetchCharacterById(id).toCharacter()
        return character!! // TODO: JLI fix this
    }

}

private fun CharacterResponse.toCharacter() = Character.Gender.fromDisplayName(gender)?.let {
    Character(
        id,
        name,
        ki,
        maxKi,
        race,
        it,
        description,
        image,
        affiliation
    )
}