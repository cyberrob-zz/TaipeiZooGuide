package com.wang.taipeizooguide.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.orhanobut.logger.Logger
import com.wang.taipeizooguide.data.model.Zoo
import com.wang.taipeizooguide.data.model.ZooQueryResult
import com.wang.taipeizooguide.data.remote.ZooRepository

class ZooViewModel(private val zooRepository: ZooRepository) : BaseViewModel() {

    val zooList = Pager(PagingConfig(pageSize = ITEM_PER_PAGE)) {
        ZooDataSource(zooRepository)
    }.flow/*.liveData*/.cachedIn(viewModelScope)

    inner class ZooDataSource(zooRepository: ZooRepository) : PagingSource<Int, Zoo>() {
        override fun getRefreshKey(state: PagingState<Int, Zoo>): Int? {
            return null
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Zoo> {
            return try {
                val currentPageKey = params.key ?: 0
                Logger.d("currentPageKey: $currentPageKey")
                val currentOffset = if (currentPageKey == 0) 0 else currentPageKey * ITEM_PER_PAGE
                Logger.d("currentOffset: $currentOffset")

                val response = zooRepository.getZooList(
                    queryString = null,
                    limit = ITEM_PER_PAGE,
                    offset = currentOffset
                )
                val data = response.data ?: emptyList<Zoo>()
                val responseData = mutableListOf<Zoo>().apply {
                    addAll((data as ZooQueryResult).results)
                }

                val prevKey = if (currentPageKey == 0) null else currentPageKey - 1

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