package dev.jlaguna.kmpballz.business.useCases

import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository

interface GetCharacterDetailUseCase {
    suspend fun execute(characterId: Int): Character
}

class GetCharacterDetailUseCaseDefault(
    private val repository: CharactersRepository
) : GetCharacterDetailUseCase {

    override suspend fun execute(characterId: Int) = repository.fetchCharacterById(characterId)
}