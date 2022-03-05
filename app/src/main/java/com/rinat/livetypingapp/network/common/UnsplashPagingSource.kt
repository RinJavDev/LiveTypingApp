package com.rinat.livetypingapp.network.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rinat.livetypingapp.network.api.BookApi
import com.rinat.livetypingapp.network.response.Book
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi: BookApi,
    private val query: String
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.getBooks(query,position*params.loadSize)
            val books = response.body()!!.items

            LoadResult.Page(
                data = books,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (books.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        TODO("Not yet implemented")
    }
}