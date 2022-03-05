package com.rinat.livetypingapp.ui.detail_book

import androidx.lifecycle.ViewModel
import com.rinat.livetypingapp.network.NetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailBookViewModel @Inject constructor(private val networkDataSource: NetworkDataSource) :
    ViewModel() {
    // TODO: Implement the ViewModel
}