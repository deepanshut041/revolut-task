package `in`.deepanshut041.revoult

import `in`.deepanshut041.revolut.data.di.networkModule
import `in`.deepanshut041.revolut.data.di.rateDataModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext( this@App)
            modules(listOf(networkModule, rateDataModule))
        }

    }
}