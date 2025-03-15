package id.fawwaz.wordle.domain.usecases

import id.fawwaz.wordle.domain.models.KeywordModel
import id.fawwaz.wordle.domain.repository.WordleRepository
import id.fawwaz.wordle.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WordleUseCase(private val repository: WordleRepository) {

    fun getRandomWord(): Flow<Result<KeywordModel>> {
        return flow {
            try {
                emit(Result.Loading)
                emit(Result.Success(repository.getRandomWord()))
            } catch (e: Exception) {
                emit(Result.Failure(e.message.orEmpty()))
            }
        }
    }

    fun searchWord(word: String): Flow<KeywordModel?> {
        return flow {
            emit(repository.searchWord(word))
        }
    }
}