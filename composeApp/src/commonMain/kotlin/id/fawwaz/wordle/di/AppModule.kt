package id.fawwaz.wordle.di

import id.fawwaz.wordle.data.WordleDataSource
import id.fawwaz.wordle.data.network.NetworkHelper
import id.fawwaz.wordle.data.network.WordleApiService
import id.fawwaz.wordle.domain.repository.WordleRepository
import id.fawwaz.wordle.domain.usecases.WordleUseCase
import id.fawwaz.wordle.viewmodels.GameViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(NetworkHelper::getHttpClient)
    singleOf(::WordleApiService)
    singleOf(::WordleDataSource)
    singleOf(::WordleRepository)
    singleOf(::WordleUseCase)
    viewModelOf(::GameViewModel)
}