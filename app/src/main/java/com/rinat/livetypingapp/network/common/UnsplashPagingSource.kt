package com.rinat.livetypingapp.network.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus
import com.google.gson.Gson
import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.mapper.mapToBookPreview
import com.rinat.livetypingapp.network.api.BookApi
import com.rinat.livetypingapp.network.response.BooksResponse
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 0

class UnsplashPagingSource(
    private val unsplashApi: BookApi,
    private val query: String
) : PagingSource<Int, BookPreview>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookPreview> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {

            val response = unsplashApi.getBooks(query, params.loadSize)
            when (response.code()) {
                HttpStatus.SC_OK -> {
                    val books =
                        response.body()?.items?.map { it.mapToBookPreview() } ?: emptyList()
                    LoadResult.Page(
                        data = books,
                        prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                        nextKey = if (books.isEmpty()) null else position + 1
                    )
                }
                HttpStatus.SC_NOT_FOUND -> {
                    LoadResult.Error(Exception("${response.code()}"))
                }
                else -> {
                    LoadResult.Error(
                        Exception(
                            "${response.code()} ${
                                Gson().fromJson(
                                    response.errorBody()?.string(), BooksResponse::class.java
                                ).error?.message
                            }"
                        )
                    )
                }
            }

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BookPreview>): Int? {
        TODO("Not yet implemented")
    }
}


