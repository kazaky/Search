package com.rolemodel

import android.app.Application
import com.rolemodel.koin.module.appModule
import com.rolemodel.koin.module.networkModule
import com.rolemodel.koin.module.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RolemodelApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@RolemodelApp)
            modules(listOf(appModule, networkModule, viewModelsModule))
        }
    }
}