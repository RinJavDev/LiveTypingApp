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
import com.rinat.livetypingapp.utils.Constants
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
        val args = getArguments()
        binding.apply {
            Glide
                .with(imageView)
                .load(args?.getString(Constants.IMAGE_URL_ARG, null))
                .error(R.drawable.error_image)
                .into(imageView)
            tvBookName.text = args?.getString(Constants.BOOK_TITLE_ARG, null)
            tvAuthor.text = args?.getString(Constants.BOOK_AUTHOR_ARG, null)
            toolbarDetailBook.setNavigationOnClickListener {
                viewModel.navigateBack()
            }
        }
    }
}