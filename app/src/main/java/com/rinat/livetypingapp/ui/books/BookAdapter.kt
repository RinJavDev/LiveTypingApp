package com.rinat.livetypingapp.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rinat.livetypingapp.databinding.IBookBinding
import com.rinat.livetypingapp.network.response.Book

class BookAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Book, BookAdapter.BookViewHolder>(BOOK_COMPARATOR) {

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

                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        if (item != null) {
                            var extras = FragmentNavigator.Extras.Builder()
                                .addSharedElement(tvBookName, "tv_book_name")
                                .addSharedElement(tvAuthor, "tv_author")
                                .addSharedElement(ivBookIcon, "imageView").build()
                            listener.onItemClick(item, extras)
                        }
                    }
                }
            }

        }

        fun bind(book: Book, position: Int) {
            binding.apply {
                ViewCompat.setTransitionName(tvBookName, "tvBookName$position")
                ViewCompat.setTransitionName(tvAuthor, "tvAuthor$position")
                ViewCompat.setTransitionName(ivBookIcon, "ivBookIcon$position")

                tvBookName.text = book.volumeInfo.title
                book.volumeInfo.authors?.let {
                    tvAuthor.text = book.volumeInfo.authors[0]
                }
                book.volumeInfo.imageLinks?.let {
                    Glide
                        .with(ivBookIcon)
                        .load(book.volumeInfo.imageLinks.thumbnail)
                        //.placeholder(R.drawable.loading_spinner)
                        .into(ivBookIcon);
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: Book, extras: FragmentNavigator.Extras)
    }

    companion object {
        private val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book) =
                oldItem.volumeInfo == newItem.volumeInfo

            override fun areContentsTheSame(oldItem: Book, newItem: Book) =
                oldItem == newItem
        }
    }
}