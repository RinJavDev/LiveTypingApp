package com.rinat.livetypingapp.network.response

data class BooksResponse(val items: List<BookModel>)

open class BookModel(val volumeInfo: VolumeInfo)

data class VolumeInfo(
    val title: String,
    val authors: Array<String>?,
    val imageLinks: ImageLink?
)

data class ImageLink(
    val smallThumbnail: String,
    val thumbnail: String
)
