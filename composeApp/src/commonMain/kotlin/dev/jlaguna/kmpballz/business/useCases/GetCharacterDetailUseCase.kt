package dev.jlaguna.kmpballz.business.useCases

import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository
import org.koin.core.component.KoinComponent

interface GetCharacterDetailUseCase {
    suspend fun execute(characterId: Int): Character
}

class GetCharacterDetailUseCaseDefault(
    private val repository: CharactersRepository
): GetCharacterDetailUseCase, KoinComponent {

    override suspend fun execute(characterId: Int) = repository.fetchCharacterById(characterId)
}