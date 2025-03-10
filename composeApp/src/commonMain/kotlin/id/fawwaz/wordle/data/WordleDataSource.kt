package id.fawwaz.wordle.data

import id.fawwaz.wordle.data.models.KeywordResponse
import id.fawwaz.wordle.data.network.WordleApiService

class WordleDataSource(private val apiService: WordleApiService) {
    suspend fun getRandomWord(): KeywordResponse {
        return apiService.getRandomWord().data ?: KeywordResponse()
    }

    suspend fun searchWord(word: String): KeywordResponse? {
        return apiService.searchWord(word).data
    }
}