package com.rinat.livetypingapp.extensions

import com.google.gson.Gson
import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.network.response.base.BaseResponse
import com.rinat.livetypingapp.network.response.base.BaseError
import com.rinat.livetypingapp.network.response.models.BookModel
import retrofit2.Response

fun BookModel.mapToBookPreview(): BookPreview {
    val volumeInfo = this.volumeInfo
    return BookPreview(
        volumeInfo.title?:"Неизвестное название",
        volumeInfo.authors?.joinToString(", ") ?: "Неизвестный автор",
        volumeInfo.imageLinks?.thumbnail ?: "",
        volumeInfo.description ?: "Описание отсутсвует"
    )
}


fun <T> Response<T>.getError(): BaseError? =
    Gson().fromJson(
        this.errorBody()?.string(), BaseResponse::class.java
    ).baseError