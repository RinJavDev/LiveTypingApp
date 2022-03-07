package com.rinat.livetypingapp.network.api

import com.rinat.livetypingapp.network.response.BooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("books/v1/volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("startIndex") index: Int
    ): Response<BooksResponse>
}