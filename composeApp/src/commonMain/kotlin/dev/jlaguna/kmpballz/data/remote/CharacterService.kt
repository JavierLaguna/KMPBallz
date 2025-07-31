package dev.jlaguna.kmpballz.data.remote

import dev.jlaguna.kmpballz.data.remote.responses.CharacterResponse
import dev.jlaguna.kmpballz.data.remote.responses.CharactersPageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CharacterService(
    private val client: HttpClient
) {

    suspend fun fetchCharacters(): CharactersPageResponse {
        return client
            .get("/characters")
            .body<CharactersPageResponse>()
    }

    suspend fun fetchCharacterById(id: Int): CharacterResponse {
        return client
            .get("/characters/$id")
            .body<CharacterResponse>()
    }
}