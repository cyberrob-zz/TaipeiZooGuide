package com.wang.taipeizooguide.data.remote

import com.wang.taipeizooguide.data.model.ArboretumQueryResult
import retrofit2.HttpException

class ArboretumRepository(
    private val openDataApi: OpenDataApi,
    private val responseHandler: ResponseHandler
) {
    suspend fun getArboretumList(
        queryString: String? = null,
        limit: Int? = null,
        offset: Int? = null
    ): Response<ArboretumQueryResult> {

        return try {
            val innerResult = openDataApi.getArboretumList(
                q = queryString,
                limit = limit,
                offset = offset
            ).result

            responseHandler.handleSuccess(innerResult)
        } catch (e: HttpException) {
            responseHandler.handleException(e.code())
        }

    }
}