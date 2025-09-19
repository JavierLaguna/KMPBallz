package dev.jlaguna.kmpballz.business.useCases

import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository

interface GetCharactersUseCase {
    suspend fun execute(): List<Character>
}

class GetCharactersUseCaseDefault(
    private val repository: CharactersRepository
) : GetCharactersUseCase {

    override suspend fun execute(): List<Character> = repository.fetchCharacters()
}