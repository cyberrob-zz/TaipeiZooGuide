package com.wang.taipeizooguide.data.remote

import com.wang.taipeizooguide.data.model.ZooQueryResult
import retrofit2.HttpException


open class ZooRepository(
    private val openDataApi: OpenDataApi,
    private val responseHandler: ResponseHandler
) {

    suspend fun getZooList(
        queryString: String? = null,
        limit: Int? = null,
        offset: Int? = null
    ): Response<ZooQueryResult> {

        return try {
            val innerResult = openDataApi.getZooList(
                q = queryString,
                limit = limit,
                offset = offset
            ).result

            responseHandler.handleSuccess(
                innerResult
            )
        } catch (e: HttpException) {
            responseHandler.handleException(e.code())
        }
    }
}