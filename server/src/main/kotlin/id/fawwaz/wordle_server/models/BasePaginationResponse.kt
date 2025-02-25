package id.fawwaz.wordle_server.models

import kotlinx.serialization.Serializable

@Serializable
data class BasePaginationResponse<T>(
    val status: String,
    val total: Int,
    val skip: Long,
    val limit: Int,
    val message: String? = null,
    val data: T? = null,
)
