package dev.jlaguna.kmpballz.business.models

data class OriginPlanet(
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String
)