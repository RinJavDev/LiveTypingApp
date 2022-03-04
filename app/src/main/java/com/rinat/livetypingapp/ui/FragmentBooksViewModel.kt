package com.rinat.livetypingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rinat.livetypingapp.network.NetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentBooksViewModel @Inject constructor(private val networkDataSource: NetworkDataSource) :
    ViewModel() {

    fun loadBooks() {
        viewModelScope.launch {
            networkDataSource.getUser("Война и мир", 0);
        }
    }

}