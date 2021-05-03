package com.wang.taipeizooguide.di

import com.wang.taipeizooguide.viewmodel.*
import org.koin.dsl.module

val viewModelModule = module {
    factory { ExampleViewModel(get(), get()) }
    factory { ZooViewModel(get()) }
    factory { ArboretumViewModel(get()) }

    factory { AttractionInfoViewModel(get()) }
    factory { ArboretumInfoViewModel() }
}