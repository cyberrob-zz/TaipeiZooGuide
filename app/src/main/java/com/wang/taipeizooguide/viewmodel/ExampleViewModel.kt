package com.wang.taipeizooguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wang.taipeizooguide.data.model.QueryParam
import com.wang.taipeizooguide.data.remote.ArboretumRepository
import com.wang.taipeizooguide.data.remote.Response
import com.wang.taipeizooguide.data.remote.ZooRepository

class ExampleViewModel(
    private val zooRepository: ZooRepository,
    private val arboretumRepository: ArboretumRepository
) : ViewModel() {

    fun getZooList(queryParam: QueryParam) = liveData {
        emit(Response.loading(null))
        emit(
            zooRepository.getZooList(
                queryString = queryParam.query,
                limit = queryParam.limit,
                offset = queryParam.offset
            )
        )
    }

    fun getArboretumList(queryParam: QueryParam) = liveData {
        emit(Response.loading(null))
        emit(
            arboretumRepository.getArboretumList(
                queryString = queryParam.query,
                limit = queryParam.limit,
                offset = queryParam.offset
            )
        )
    }
}