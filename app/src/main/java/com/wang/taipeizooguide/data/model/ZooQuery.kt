package com.wang.taipeizooguide.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ZooQuery(
    val result: ZooQueryResult
) : Parcelable

@Parcelize
data class ZooQueryResult(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val sort: String,
    val results: List<Zoo>
) : Parcelable

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
