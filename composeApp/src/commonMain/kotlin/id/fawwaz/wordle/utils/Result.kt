package id.fawwaz.wordle.utils

sealed class Result<out T>() {
    object Initial : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val message: String) : Result<Nothing>()
}