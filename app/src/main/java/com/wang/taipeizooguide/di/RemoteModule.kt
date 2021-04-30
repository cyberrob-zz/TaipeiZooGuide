package com.wang.taipeizooguide.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wang.taipeizooguide.BuildConfig
import com.wang.taipeizooguide.data.remote.OpenDataApi
import com.wang.taipeizooguide.data.remote.ResponseHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteModule = module {
    factory { provideInterceptor() }
    factory { provideMoshi() }
    factory { provideOkHttpClient(get()) }
    factory { provideOpenDataApi(get()) }
    factory { ResponseHandler() }
    single { provideRetrofit(get(), get()) }
}


fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun provideInterceptor(): HttpLoggingInterceptor {
    return when {
        BuildConfig.BUILD_TYPE.toLowerCase().contains("release") -> {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { level = HttpLoggingInterceptor.Level.NONE }
        }
        else -> {
            val interceptor = HttpLoggingInterceptor()
//                object : HttpLoggingInterceptor.Logger {
//                    override fun log(message: String) {
//                        Logger.t(HTTP_TRAFFIC).d(message)
//                    }
//                })

            interceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
        }
    }
}

fun provideOpenDataApi(retrofit: Retrofit): OpenDataApi = retrofit.create(OpenDataApi::class.java)