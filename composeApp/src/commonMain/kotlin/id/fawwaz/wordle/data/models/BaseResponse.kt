package id.fawwaz.wordle.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: String? = null,
    val message: String? = null,
    val total: Int? = null,
    val data: T? = null
)
