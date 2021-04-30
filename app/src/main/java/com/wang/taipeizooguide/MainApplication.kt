package com.wang.taipeizooguide

import android.app.Application
import com.wang.taipeizooguide.di.arboretumModule
import com.wang.taipeizooguide.di.remoteModule
import com.wang.taipeizooguide.di.zooModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    remoteModule,
                    zooModule,
                    arboretumModule
                )
            )
        }
    }
}