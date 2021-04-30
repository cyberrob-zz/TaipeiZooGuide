package com.wang.taipeizooguide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wang.taipeizooguide.data.model.QueryParam
import com.wang.taipeizooguide.data.model.ZooQueryResult
import com.wang.taipeizooguide.data.remote.ZoomRepository
import kotlinx.coroutines.Dispatchers

class ExampleViewModel(private val zooRepository: ZoomRepository) : ViewModel() {

    var queryParam: QueryParam? = null

    val zoomList: LiveData<ZooQueryResult> = liveData(Dispatchers.IO) {
        emit(Resource.)
        emit(
            zooRepository.getZoomList(
                queryString = queryParam?.query,
                limit = queryParam?.limit,
                offset = queryParam?.offset
            )
        )
    }


}