package com.rinat.livetypingapp.network.response

data class BooksResponse(val items: List<Book>)

data class Book(val volumeInfo: VolumeInfo)

data class VolumeInfo(
    val title: String,
    val authors: Array<String>

)
