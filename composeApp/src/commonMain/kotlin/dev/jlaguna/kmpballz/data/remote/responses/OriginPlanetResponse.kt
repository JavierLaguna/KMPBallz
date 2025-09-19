package dev.jlaguna.kmpballz.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class OriginPlanetResponse(
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String
)