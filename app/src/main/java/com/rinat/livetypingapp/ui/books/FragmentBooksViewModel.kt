package com.rinat.livetypingapp.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.network.NetworkDataSource
import com.rinat.livetypingapp.router.MainRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FragmentBooksViewModel @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    val router: MainRouter
) :
    ViewModel() {


    private var _booksFlow: Flow<PagingData<BookPreview>>? = null
    val booksFlow: Flow<PagingData<BookPreview>>?
        get() = _booksFlow

    fun getBooks(query: String): Flow<PagingData<BookPreview>>? {
        _booksFlow = networkDataSource.getSearchResults(query).cachedIn(viewModelScope)
        return booksFlow
    }

    fun openDetail(bookPreview: BookPreview, extras: FragmentNavigator.Extras) {
        router.navigateToDetailBookInfo(bookPreview, extras)
    }
}