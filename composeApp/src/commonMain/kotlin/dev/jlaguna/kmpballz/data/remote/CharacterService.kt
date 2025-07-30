package dev.jlaguna.kmpballz.data.remote

import dev.jlaguna.kmpballz.data.models.CharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CharacterService(
    private val client: HttpClient
) {
    // https://dragonball-api.com/api/characters

    suspend fun fetchCharacterById(id: Int): CharacterResponse {
        return client
            .get("/characters/$id")
            .body<CharacterResponse>()
    }
}