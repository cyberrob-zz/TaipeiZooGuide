package com.wang.taipeizooguide

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.wang.taipeizooguide.di.arboretumModule
import com.wang.taipeizooguide.di.remoteModule
import com.wang.taipeizooguide.di.viewModelModule
import com.wang.taipeizooguide.di.zooModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    remoteModule,
                    zooModule,
                    arboretumModule,
                    viewModelModule
                )
            )
        }

        // Only debug build can print log messages.
        // true will print the log message, false will ignore it.
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}