package com.wang.taipeizooguide.di

import com.wang.taipeizooguide.data.remote.ZoomRepository
import org.koin.dsl.module

val arboretumModule = module {
    factory { ZoomRepository(get()) }
}