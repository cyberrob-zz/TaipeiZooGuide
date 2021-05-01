package com.wang.taipeizooguide.di

import com.wang.taipeizooguide.viewmodel.ExampleViewModel
import com.wang.taipeizooguide.viewmodel.ZooViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { ExampleViewModel(get(), get()) }
    factory { ZooViewModel(get()) }
}