package com.rinat.livetypingapp.ui.detail_book

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.rinat.livetypingapp.R
import com.rinat.livetypingapp.databinding.FDetailBookBinding
import com.rinat.livetypingapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBookFragment : Fragment(R.layout.f_detail_book) {

    companion object {
        fun newInstance() = DetailBookFragment()
    }

    private val binding: FDetailBookBinding by viewBinding(FDetailBookBinding::bind)
    private val viewModel: DetailBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var args = getArguments()
        binding.apply {

            args?.getString(Constants.IMAGE_URL_ARG, null)?.let {
                Glide
                    .with(imageView)
                    .load(it)
                    //.placeholder(R.drawable.loading_spinner)
                    .into(imageView);
            }
            args?.getString(Constants.BOOK_TITLE_ARG, null)?.let {
                tvBookName.text = it
            }
            args?.getString(Constants.BOOK_AUTHOR_ARG, null)?.let {
                tvAuthor.text = it
            }
        }
    }
}