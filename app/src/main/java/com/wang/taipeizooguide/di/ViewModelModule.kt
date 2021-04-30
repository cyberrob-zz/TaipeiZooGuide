package com.wang.taipeizooguide.di

import com.wang.taipeizooguide.viewmodel.ExampleViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { ExampleViewModel(get(), get()) }
}