package dev.jlaguna.kmpballz.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class CharacterTransformationResponse(
    val id: Int,
    val name: String,
    val image: String,
    val ki: String
)