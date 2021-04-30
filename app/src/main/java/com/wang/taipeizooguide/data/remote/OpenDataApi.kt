package com.wang.taipeizooguide.data.remote

import com.wang.taipeizooguide.data.model.ArboretumQuery
import com.wang.taipeizooguide.data.model.ZooQuery
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface OpenDataApi {

    @GET("f18de02f-b6c9-47c0-8cda-50efad621c14")
    fun getArboretumList(
        scope: String = "resourceAquire",
        q: String?,
        limit: Int?,
        offset: Int?
    ): Deferred<ArboretumQuery>

    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    fun getZooList(
        scope: String = "resourceAquire",
        q: String?,
        limit: Int?,
        offset: Int?
    ): Deferred<ZooQuery>
}