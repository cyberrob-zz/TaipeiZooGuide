package com.wang.taipeizooguide.data.remote


class ZoomRepository(private val openDataApi: OpenDataApi) {
    suspend fun getZoomList(queryString: String?, limit: Int?, offset: Int?) =
        openDataApi.getZooList(
            q = queryString,
            limit = limit,
            offset = offset
        ).await().result
}