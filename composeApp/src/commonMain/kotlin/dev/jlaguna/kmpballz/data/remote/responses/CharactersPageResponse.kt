package dev.jlaguna.kmpballz.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class CharactersPageResponse(
    val items: List<CharacterResponse>,
    val meta: PageMetaResponse
)