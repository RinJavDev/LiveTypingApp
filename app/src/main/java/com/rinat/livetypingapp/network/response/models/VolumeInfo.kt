package com.rinat.livetypingapp.network.response.models

data class VolumeInfo(
    val title: String,
    val authors: Array<String>?,
    val imageLinks: ImageLink?,
    val description: String?
)