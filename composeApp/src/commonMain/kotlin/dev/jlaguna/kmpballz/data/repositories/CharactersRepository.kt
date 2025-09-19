package dev.jlaguna.kmpballz.data.repositories

import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.business.models.CharacterTransformation
import dev.jlaguna.kmpballz.business.models.OriginPlanet
import dev.jlaguna.kmpballz.data.remote.CharacterService
import dev.jlaguna.kmpballz.data.remote.responses.CharacterResponse
import dev.jlaguna.kmpballz.data.remote.responses.CharacterTransformationResponse
import dev.jlaguna.kmpballz.data.remote.responses.OriginPlanetResponse

class CharactersRepository(
    private val characterService: CharacterService
) {

    private var page = 1
    private var totalPages: Int? = null

    suspend fun fetchCharacters(): List<Character> {
        if (totalPages != null && page > totalPages!!) {
            return emptyList() // TODO: JLI Throw error
        }

        val response = characterService.fetchCharacters(page)
        totalPages = response.meta.totalPages
        page++
        return response.items.mapNotNull { it.toCharacter() }
    }

    suspend fun fetchCharacterById(id: Int): Character {
        val character = characterService.fetchCharacterById(id).toCharacter()
        return character!! // TODO: JLI fix this
    }

}

private fun CharacterResponse.toCharacter() = Character.Gender.fromDisplayName(gender)?.let { it ->
    Character(
        id,
        name,
        ki,
        maxKi,
        race,
        it,
        description,
        image,
        affiliation,
        originPlanet?.toOriginPlanet(),
        transformations?.map { it.toCharacterTransformation() } ?: emptyList()
    )
}

private fun OriginPlanetResponse.toOriginPlanet() = OriginPlanet(
    id,
    name,
    isDestroyed,
    description,
    image
)

private fun CharacterTransformationResponse.toCharacterTransformation() = CharacterTransformation(
    id,
    name,
    image,
    ki
)