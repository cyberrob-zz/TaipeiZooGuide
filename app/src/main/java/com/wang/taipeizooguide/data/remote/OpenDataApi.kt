package com.wang.taipeizooguide.data.remote

import com.wang.taipeizooguide.data.model.ArboretumQuery
import com.wang.taipeizooguide.data.model.ZooQuery
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenDataApi {

    @GET("f18de02f-b6c9-47c0-8cda-50efad621c14")
    suspend fun getArboretumList(
        @Query("scope") scope: String = "resourceAquire",
        @Query("q") q: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): ArboretumQuery

    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    suspend fun getZooList(
        @Query("scope") scope: String = "resourceAquire",
        @Query("q") q: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): ZooQuery
}