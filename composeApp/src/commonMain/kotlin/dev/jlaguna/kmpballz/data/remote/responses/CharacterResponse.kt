package dev.jlaguna.kmpballz.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
    val affiliation: String,
    val transformations: List<CharacterTransformationResponse>? = null
)
