package com.rinat.livetypingapp.mapper

import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.network.response.BookModel

fun BookModel.mapToBookPreview(): BookPreview {
    val volumeInfo = this.volumeInfo
    return BookPreview(
        volumeInfo.title,
        volumeInfo.authors?.joinToString(", ") ?: "Неизвестный автор",
        volumeInfo.imageLinks?.thumbnail ?: ""
    )
}