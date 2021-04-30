package com.wang.taipeizooguide.data.remote

import com.wang.taipeizooguide.data.model.*

data class Response<out T>(val status: ApiStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(data: T?): Response<T> =
            Response(status = ApiStatus.LOADING, data = data, message = null)

        fun <T> success(data: T): Response<T> =
            Response(status = ApiStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Response<T> =
            Response(status = ApiStatus.ERROR, data = data, message = message)

        fun <T> empty(data: T?): Response<T> =
            Response(status = ApiStatus.EMPTY, data = data, message = null)
    }
}

class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Response<T> {
        return if (data is BaseQueryResult && data.results.isEmpty()) Response.empty(data)
        else Response.success(data)
    }

    fun <T : Any> handleException(code: Int): Response<T> {
        return Response.error(data = null, message = getErrorMessage(code))
    }

    private fun getErrorMessage(httpCode: Int): String {
        return when (httpCode) {
            STATUS_BAD_REQUEST -> "Bad Request"
            STATUS_UNAUTHORIZED -> "Unauthorised"
            STATUS_FORBIDDEN -> "Forbidden"
            STATUS_NOT_FOUND -> "Not found"
            STATUS_SERVER_ERROR -> "Service Error"
            else -> "Something went wrong"
        }
    }
}