package dev.jlaguna.kmpballz.business.useCases

import dev.jlaguna.kmpballz.business.useCases.models.Character
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository
import org.koin.core.component.KoinComponent

interface GetCharactersUseCase {
    suspend fun getCharacters(): List<Character>
}

class GetCharactersUseCaseDefault(
    private val repository: CharactersRepository
): GetCharactersUseCase, KoinComponent {

    override suspend fun getCharacters(): List<Character> = repository.fetchCharacters()
}