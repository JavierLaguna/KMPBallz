package dev.jlaguna.kmpballz.data.models

import kotlinx.serialization.Serializable

data class Character (
    val id: Int,
    val name: String,
    val description: String
)

@Serializable
data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String
    //@SerialName("release_date") val releaseDate: String,
)