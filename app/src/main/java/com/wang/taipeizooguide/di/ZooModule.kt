package com.wang.taipeizooguide.di

import com.wang.taipeizooguide.data.remote.ZoomRepository
import org.koin.dsl.module

val zooModule = module {
    factory { ZoomRepository(get()) }
}