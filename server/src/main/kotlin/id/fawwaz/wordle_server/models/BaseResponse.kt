package id.fawwaz.wordle_server.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: String,
    val total: Int,
    val message: String? = null,
    val data: T? = null
)
