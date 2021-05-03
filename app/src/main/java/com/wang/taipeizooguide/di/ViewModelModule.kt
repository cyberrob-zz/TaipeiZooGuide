package com.wang.taipeizooguide.di

import com.wang.taipeizooguide.viewmodel.ArboretumViewModel
import com.wang.taipeizooguide.viewmodel.AttractionInfoViewModel
import com.wang.taipeizooguide.viewmodel.ExampleViewModel
import com.wang.taipeizooguide.viewmodel.ZooViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { ExampleViewModel(get(), get()) }
    factory { ZooViewModel(get()) }
    factory { ArboretumViewModel(get()) }

    factory { AttractionInfoViewModel(get()) }
}