package com.contraomnese.coffee.app

import android.app.Application
import com.contraomnese.coffee.di.appModule
import com.contraomnese.coffee.di.architecturePresentationModule
import com.contraomnese.coffee.di.dataModule
import com.contraomnese.coffee.di.domainModule
import com.yandex.mapkit.MapKitFactory
import com.contraomnese.coffee.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAPKIT_KEY)
        MapKitFactory.initialize(this)
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule,
                architecturePresentationModule,
                dataModule,
                domainModule
            )
        }
    }

}