package com.rinat.livetypingapp.ui.books

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rinat.livetypingapp.R
import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.databinding.FBooksBinding
import com.rinat.livetypingapp.utils.SimpleDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class FragmentBooks : Fragment(R.layout.f_books), BookAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener {


    companion object {
        fun newInstance() = FragmentBooks()
    }

    private val binding: FBooksBinding by viewBinding(FBooksBinding::bind)
    private val viewModel: FragmentBooksViewModel by viewModels()
    val adapter = BookAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        (view).doOnPreDraw {
            startPostponedEnterTransition()
        }

        binding.apply {
            rvBooks.addItemDecoration(
                SimpleDividerItemDecoration(
                    requireContext(),
                    R.drawable.divider_line
                )
            )
            rvBooks.setHasFixedSize(true)
            rvBooks.layoutManager = LinearLayoutManager(requireContext())
            rvBooks.itemAnimator = null
            rvBooks.adapter = adapter.withLoadStateHeaderAndFooter(
                header = BookLoadStateAdapter { adapter.retry() },
                footer = BookLoadStateAdapter { adapter.retry() }
            )
            svBooks.setOnQueryTextListener(this@FragmentBooks)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.booksFlow?.collect {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                val loadStateSourceRefresh = loadState.source.refresh
                rvBooks.isVisible = true
                progressBar.isVisible = loadStateSourceRefresh is LoadState.Loading
                if (loadStateSourceRefresh is LoadState.Error) {
                    Toast.makeText(
                        requireContext(),
                        "${loadStateSourceRefresh.error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // empty view
                if (loadStateSourceRefresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvBooks.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
    }

    override fun onItemClick(bookPreview: BookPreview, extras: FragmentNavigator.Extras) {
        viewModel.openDetail(bookPreview, extras)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        query?.let {
            lifecycleScope.launchWhenCreated {
                viewModel.getBooks(query)?.collect {
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }
        binding.svBooks.clearFocus()
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return false
    }

}