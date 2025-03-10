package id.fawwaz.wordle.data.network

import id.fawwaz.wordle.data.models.BaseResponse
import id.fawwaz.wordle.data.models.KeywordResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class WordleApiService(private val httpClient: HttpClient) {

    suspend fun getRandomWord(): BaseResponse<KeywordResponse> {
        return httpClient.get("words/random").body()
    }

    suspend fun searchWord(word: String): BaseResponse<KeywordResponse?> {
        return httpClient.get("words/search") {
            url { appendPathSegments(word) }
        }.body()
    }
}