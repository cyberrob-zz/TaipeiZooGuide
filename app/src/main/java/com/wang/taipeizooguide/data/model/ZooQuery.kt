package com.wang.taipeizooguide.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ZooQuery(
    val result: ZooQueryResult
)

data class ZooQueryResult(
    override val count: Int?,
    override val limit: Int,
    override val offset: Int,
    override val sort: String,
    override val results: List<Zoo>
) : BaseQueryResult(count, limit, offset, sort, results)

@Parcelize
data class Zoo(
    val E_Category: String,
    val E_Geo: String,
    val E_Info: String,
    val E_Memo: String,
    val E_Name: String,
    val E_Pic_URL: String,
    val E_URL: String,
    val E_no: String,
    val _id: Int
) : Parcelable
