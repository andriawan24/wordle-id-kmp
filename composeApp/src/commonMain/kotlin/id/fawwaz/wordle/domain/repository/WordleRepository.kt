package id.fawwaz.wordle.domain.repository

import id.fawwaz.wordle.data.WordleDataSource
import id.fawwaz.wordle.domain.models.KeywordModel

class WordleRepository(private val dataSource: WordleDataSource) {
    suspend fun getRandomWord(): KeywordModel {
        return KeywordModel.from(dataSource.getRandomWord())
    }

    suspend fun searchWord(word: String): KeywordModel? {
        return dataSource.searchWord(word)?.let { KeywordModel.from(it) }
    }
}