package com.wang.taipeizooguide.data.model

const val STATUS_OK = 200
const val STATUS_CREATED = 201
const val STATUS_NO_CONTENT = 204
const val STATUS_BAD_REQUEST = 400
const val STATUS_UNAUTHORIZED = 401
const val STATUS_FORBIDDEN = 403
const val STATUS_NOT_FOUND = 404
const val STATUS_SERVER_ERROR = 500
const val HTTP_TRAFFIC = ">>>>> HTTP "

data class QueryParam(
    val query: String? = null,
    val limit: Int? = null,
    val offset: Int? = null
)

enum class ApiStatus {
    LOADING,
    SUCCESS,
    ERROR,
    EMPTY
}

