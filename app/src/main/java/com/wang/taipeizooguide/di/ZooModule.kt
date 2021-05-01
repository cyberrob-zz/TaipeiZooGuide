package com.wang.taipeizooguide.di

import com.wang.taipeizooguide.data.remote.ZooRepository
import org.koin.dsl.module

val zooModule = module {
    factory { ZooRepository(get(), get()) }
}