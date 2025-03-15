package id.fawwaz.wordle

import android.app.Application
import id.fawwaz.wordle.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoinModules()
    }

    private fun initKoinModules() {
        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
        }
    }
}