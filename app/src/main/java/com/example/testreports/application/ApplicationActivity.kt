package com.example.testreports.application

import android.app.Application
import com.smartserve.pos.Utils.Application.appModules
import com.smartserve.pos.Utils.Application.viewModelModules

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class ApplicationActivity: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@ApplicationActivity)
            modules(listOf(appModules, viewModelModules))
        }


    }


}