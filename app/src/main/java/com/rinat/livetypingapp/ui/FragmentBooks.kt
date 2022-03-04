package com.rinat.livetypingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rinat.livetypingapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBooks : Fragment() {

    companion object {
        fun newInstance() = FragmentBooks()
    }

    private val viewModel: FragmentBooksViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_books, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadBooks()
    }


}