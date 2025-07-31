package dev.jlaguna.kmpballz.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class PageMetaResponse(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)