package com.wang.taipeizooguide.data.remote

class ArboretumRepository(private val openDataApi: OpenDataApi) {
    suspend fun getArboretumList(queryString: String, limit: Int, offset: Int) =
        openDataApi.getArboretumList(
            q = queryString,
            limit = limit,
            offset = offset
        ).await().result
}