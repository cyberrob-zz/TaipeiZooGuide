package com.wang.taipeizooguide.di

import com.wang.taipeizooguide.data.remote.ArboretumRepository
import org.koin.dsl.module

val arboretumModule = module {
    factory { ArboretumRepository(get(), get()) }
}