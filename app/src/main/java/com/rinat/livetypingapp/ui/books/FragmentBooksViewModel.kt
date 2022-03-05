package com.rinat.livetypingapp.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rinat.livetypingapp.network.NetworkDataSource
import com.rinat.livetypingapp.network.response.Book
import com.rinat.livetypingapp.router.MainRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FragmentBooksViewModel @Inject constructor(private val networkDataSource: NetworkDataSource) :
    ViewModel() {
    @Inject
    lateinit var router: MainRouter

    private var _booksFlow: Flow<PagingData<Book>> = networkDataSource.getSearchResults("")
    val booksFlow: Flow<PagingData<Book>>
        get() = _booksFlow

    fun getBooks(query: String): Flow<PagingData<Book>> {
        _booksFlow = networkDataSource.getSearchResults(query).cachedIn(viewModelScope)
        return booksFlow
    }

    fun openDetail(book: Book) {
        router.navigateToDetailBookInfo(book)
    }
}