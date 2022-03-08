package com.rinat.livetypingapp.ui.detail_book

import androidx.lifecycle.ViewModel
import com.rinat.livetypingapp.router.MainRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailBookViewModel @Inject constructor(
    val router: MainRouter
) :
    ViewModel() {

    fun navigateBack() {
        router.onBack()
    }
}