package com.rinat.livetypingapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rinat.livetypingapp.R
import com.rinat.livetypingapp.databinding.FBooksBinding
import com.rinat.livetypingapp.network.response.Book
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FragmentBooks : Fragment(R.layout.f_books), BookAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = FragmentBooks()
    }

    private val binding: FBooksBinding by viewBinding(FBooksBinding::bind)
    private val viewModel: FragmentBooksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BookAdapter(this)

        binding.apply {
            rvBooks.setHasFixedSize(true)
            rvBooks.layoutManager=LinearLayoutManager(requireContext())
            rvBooks.itemAnimator = null
            rvBooks.adapter = adapter.withLoadStateHeaderAndFooter(
                header = BookLoadStateAdapter { adapter.retry() },
                footer = BookLoadStateAdapter { adapter.retry() }
            )
          //  buttonRetry.setOnClickListener { adapter.retry() }
        }

       lifecycleScope.launchWhenCreated {
           viewModel.getBooks("Марвел").collect {
               adapter.submitData(viewLifecycleOwner.lifecycle, it)
           }
       }



        adapter.addLoadStateListener { loadState ->
            binding.apply {
                rvBooks.isVisible=true
                //progressBar.isVisible = loadState.source.refresh is LoadState.Loading
               //rvBooks.isVisible = loadState.source.refresh is LoadState.NotLoading
                //buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                //textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                   // rvBooks.isVisible = false
                  //  textViewEmpty.isVisible = true
                } else {
                 //   textViewEmpty.isVisible = false
                }
            }
        }
    }

    override fun onItemClick(photo: Book) {

    }
}