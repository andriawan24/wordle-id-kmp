package id.fawwaz.wordle.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkHelper {
    fun getHttpClient(): HttpClient = HttpClient(CIO) {
        expectSuccess = true

        install(Logging)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        defaultRequest {
            url("http://192.168.5.17:8080/")
        }

        engine {
            maxConnectionsCount = 1000
            endpoint {
                maxConnectionsCount = 100
                pipelineMaxSize = 20
                keepAliveTime = 5000
                connectTimeout = 5000
                connectAttempts = 5
            }
        }
    }
}