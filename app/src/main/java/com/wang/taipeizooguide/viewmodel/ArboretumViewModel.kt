package com.wang.taipeizooguide.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.orhanobut.logger.Logger
import com.wang.taipeizooguide.data.model.Arboretum
import com.wang.taipeizooguide.data.model.ArboretumQueryResult
import com.wang.taipeizooguide.data.remote.ArboretumRepository

class ArboretumViewModel(private val arboretumRepository: ArboretumRepository) : BaseViewModel() {

    val arboretumList = Pager(PagingConfig(pageSize = ITEM_PER_PAGE)) {
        ArboretumDataSource(arboretumRepository)
    }.flow/*.liveData*/.cachedIn(viewModelScope)

    inner class ArboretumDataSource(arboretumRepository: ArboretumRepository) :
        PagingSource<Int, Arboretum>() {
        override fun getRefreshKey(state: PagingState<Int, Arboretum>): Int? {
            return null
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Arboretum> {
            return try {
                val currentPageKey = params.key ?: 0

                val currentOffset = if (currentPageKey == 0) 0 else currentPageKey * ITEM_PER_PAGE

                val response = arboretumRepository.getArboretumList(
                    limit = ITEM_PER_PAGE,
                    offset = currentOffset
                )

                val responseData =
                    ((response.data as? ArboretumQueryResult)?.results) ?: listOf()

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