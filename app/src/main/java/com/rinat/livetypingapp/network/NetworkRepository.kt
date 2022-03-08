package com.rinat.livetypingapp.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rinat.livetypingapp.network.api.BookApi
import com.rinat.livetypingapp.network.common.UnsplashPagingSource
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val mainService: BookApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 40,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(mainService, query) }
        ).flow

    suspend fun getBooks(query: String, index: Int) =
        mainService.getBooks(query, index, query).body()
}