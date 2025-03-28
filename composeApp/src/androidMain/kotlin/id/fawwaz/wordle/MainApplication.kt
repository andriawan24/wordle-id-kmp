package id.fawwaz.wordle

import android.app.Application
import id.fawwaz.wordle.di.initKoin
import io.kotzilla.sdk.KotzillaSDK
import io.kotzilla.sdk.analytics.koin.analytics
import io.kotzilla.sdk.analytics.koin.analyticsLogger
import io.kotzilla.sdk.android.security.apiKey
import io.kotzilla.sdk.getVersionName
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoinModules()
    }

    private fun initKoinModules() {
        // TODO: Move this code to shared module
        val instance = KotzillaSDK
            .setup(apiKey(), getVersionName())
            .connect()

        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
            // TODO: Move this code to shared module
            analytics {
                setApiKey(apiKey())
                setVersion(getVersionName())
            }
            analyticsLogger(sdkInstance = instance)
        }
    }
}