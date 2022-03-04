package com.rinat.livetypingapp.network

import com.rinat.livetypingapp.network.api.BookApi
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val mainService: BookApi) {
    suspend fun getUser(query: String, index: Int) = mainService.getBooks(query, index)
}