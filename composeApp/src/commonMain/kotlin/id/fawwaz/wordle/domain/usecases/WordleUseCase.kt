package id.fawwaz.wordle.domain.usecases

import id.fawwaz.wordle.domain.models.KeywordModel
import id.fawwaz.wordle.domain.repository.WordleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WordleUseCase(private val repository: WordleRepository) {

    fun getRandomWord(): Flow<KeywordModel> {
        return flow {
            emit(repository.getRandomWord())
        }
    }

    fun searchWord(word: String): Flow<KeywordModel?> {
        return flow {
            emit(repository.searchWord(word))
        }
    }
}