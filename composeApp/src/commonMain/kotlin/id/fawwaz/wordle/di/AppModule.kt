package id.fawwaz.wordle.di

import id.fawwaz.wordle.data.WordleDataSource
import id.fawwaz.wordle.data.network.WordleApiService
import id.fawwaz.wordle.domain.repository.WordleRepository
import id.fawwaz.wordle.domain.usecases.WordleUseCase
import id.fawwaz.wordle.viewmodels.GameViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> {
        HttpClient(CIO) {
            expectSuccess = true

            install(Logging)
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            defaultRequest {
                url("http://192.168.1.6:8080/")
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

    singleOf(::WordleApiService)
    singleOf(::WordleDataSource)
    singleOf(::WordleRepository)
    singleOf(::WordleUseCase)
    viewModelOf(::GameViewModel)
}