package com.rinat.livetypingapp.network.response

import com.rinat.livetypingapp.network.response.base.BaseResponse
import com.rinat.livetypingapp.network.response.models.BookModel

data class BooksResponse(val items: List<BookModel>?) : BaseResponse()






