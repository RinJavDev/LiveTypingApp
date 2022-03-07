package com.rinat.livetypingapp.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rinat.livetypingapp.R
import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.databinding.IBookBinding
import com.rinat.livetypingapp.network.response.BookModel

class BookAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<BookPreview, BookAdapter.BookViewHolder>(BOOK_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            IBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem, position)
        }
    }

    inner class BookViewHolder(private val binding: IBookBinding) :
        RecyclerView.ViewHolder(binding.root) {



        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    println("bindingAdapterPosition is $bindingAdapterPosition")
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        if (item != null) {
                            val extras = FragmentNavigator.Extras.Builder()
                                .addSharedElement(tvBookName, "tv_book_name")
                                .addSharedElement(tvAuthor, "tv_author")
                                .addSharedElement(ivBookIcon, "imageView").build()
                            listener.onItemClick(item, extras)
                        }
                    }
                }
            }

        }

        fun bind(bookPreview: BookPreview, position: Int) {
            binding.apply {
                ViewCompat.setTransitionName(tvBookName, "tvBookName$position")
                ViewCompat.setTransitionName(tvAuthor, "tvAuthor$position")
                ViewCompat.setTransitionName(ivBookIcon, "ivBookIcon$position")

                tvBookName.text = bookPreview.name

                    tvAuthor.text = bookPreview.author


                    Glide
                        .with(ivBookIcon)
                        .load(bookPreview.imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivBookIcon)

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(bookPreview: BookPreview, extras: FragmentNavigator.Extras)
    }

    companion object {
        private val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<BookPreview>() {
            override fun areItemsTheSame(oldItem: BookPreview, newItem: BookPreview) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: BookPreview, newItem: BookPreview) =
                oldItem == newItem
        }
    }
}