package com.wang.taipeizooguide.data.model

open class BaseQueryResult(
    open val count: Int,
    open val limit: Int,
    open val offset: Int,
    open val sort: String,
    open val results: List<Any>
)