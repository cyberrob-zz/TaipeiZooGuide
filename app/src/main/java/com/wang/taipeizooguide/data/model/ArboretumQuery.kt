package com.wang.taipeizooguide.data.model

data class ArboretumQuery(
    val result: ArboretumQueryResult
)

data class ArboretumQueryResult(
    override val count: Int,
    override val limit: Int,
    override val offset: Int,
    override val sort: String,
    override val results: List<Arboretum>
) : BaseQueryResult(count, limit, offset, sort, results)

data class Arboretum(
    val F_AlsoKnown: String,
    val F_Brief: String,
    val F_CID: String,
    val F_Code: String,
    val F_Family: String,
    val F_Feature: String,
    val F_Genus: String,
    val F_Geo: String,
    val F_Keywords: String,
    val F_Location: String,
    val F_Name_En: String,
    val F_Name_Latin: String,
    val F_Pic01_ALT: String,
    val F_Pic01_URL: String,
    val F_Pic02_ALT: String,
    val F_Pic02_URL: String,
    val F_Pic03_ALT: String,
    val F_Pic03_URL: String,
    val F_Pic04_ALT: String,
    val F_Pic04_URL: String,
    val F_Summary: String,
    val F_Update: String,
    val F_Vedio_URL: String,
    val F_Voice01_ALT: String,
    val F_Voice01_URL: String,
    val F_Voice02_ALT: String,
    val F_Voice02_URL: String,
    val F_Voice03_ALT: String,
    val F_Voice03_URL: String,
    val F_pdf01_ALT: String,
    val F_pdf01_URL: String,
    val F_pdf02_ALT: String,
    val F_pdf02_URL: String,
//    val F_Name_Ch: String,
    val _id: Int
)
