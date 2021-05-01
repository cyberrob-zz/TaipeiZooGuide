package com.wang.taipeizooguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.wang.taipeizooguide.data.model.Zoo
import com.wang.taipeizooguide.data.model.ZooQueryResult
import com.wang.taipeizooguide.data.remote.ZooRepository

class ZooViewModel(private val zooRepository: ZooRepository) : ViewModel() {

    //    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
//
    private val ITEM_PER_PAGE = 20

    val zooList = Pager(PagingConfig(pageSize = ITEM_PER_PAGE)) {
        ZooDataSource(zooRepository)
    }.flow/*.liveData*/.cachedIn(viewModelScope)

    inner class ZooDataSource(zooRepository: ZooRepository) : PagingSource<Int, Zoo>() {
        override fun getRefreshKey(state: PagingState<Int, Zoo>): Int? {
            return null
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Zoo> {
            return try {
                val currentPageKey = params.key ?: 1
                val response = zooRepository.getZooList(
                    queryString = null,
                    limit = ITEM_PER_PAGE,
                    offset = if (currentPageKey == 1) 0 else currentPageKey * ITEM_PER_PAGE
                )
                val data = response.data ?: emptyList<Zoo>()
                val responseData = mutableListOf<Zoo>().apply {
                    addAll((data as ZooQueryResult).results)
                }

                val prevKey = if (currentPageKey == 1) null else currentPageKey - 1

                LoadResult.Page(
                    data = responseData,
                    prevKey = prevKey,
                    nextKey = currentPageKey.plus(1)
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

    }

}