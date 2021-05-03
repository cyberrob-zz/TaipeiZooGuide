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

                val currentOffset = if (currentPageKey == 0) 0 else currentPageKey * ITEM_PER_PAGE

                val response = zooRepository.getZooList(
                    queryString = null,
                    limit = ITEM_PER_PAGE,
                    offset = currentOffset
                )

                val responseData =
                    if ((response.data as? ZooQueryResult)?.results.isNullOrEmpty()) listOf<Zoo>()
                    else (response.data as? ZooQueryResult)?.results ?: listOf()

                val prevKey = if (currentPageKey == 0) null else currentPageKey - 1

                val nextKey = if (responseData.isNullOrEmpty()) null else currentPageKey + 1

                LoadResult.Page(
                    data = responseData,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } catch (e: Exception) {
                Logger.e(e.toString())
                LoadResult.Error(e)
            }
        }
    }

}