package com.rinat.livetypingapp

import com.google.gson.Gson
import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.extensions.getError
import com.rinat.livetypingapp.extensions.mapToBookPreview
import com.rinat.livetypingapp.network.response.base.BaseError
import com.rinat.livetypingapp.network.response.base.BaseResponse
import com.rinat.livetypingapp.network.response.models.BookModel
import com.rinat.livetypingapp.network.response.models.ImageLink
import com.rinat.livetypingapp.network.response.models.VolumeInfo
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExtensionsTests {

    val gson = Gson()

    @Test
    fun mapEmptyBookModelTest() {
        val expectedBookPreview = BookPreview()
        assertEquals(
            expectedBookPreview,
            BookModel(VolumeInfo(null, null, null, null)).mapToBookPreview()
        )
    }

    @Test
    fun mapBookModelTest() {
        val expectedBookPreview = BookPreview(
            name = "Фантастика",
            "Л.Н. Толстой, Н.В. Гоголь",
            "https://www.google.ru/",
            "Описание"
        )
        assertEquals(
            expectedBookPreview,
            BookModel(
                VolumeInfo(
                    "Фантастика",
                    arrayOf("Л.Н. Толстой", "Н.В. Гоголь"),
                    ImageLink("https://www.google.ru/", "https://www.google.ru/"),
                    "Описание"
                )
            ).mapToBookPreview()
        )
    }

    @Test
    fun mapEmptyErrorFunctionTest() {

        val response = Mockito.mock(Response::class.java)
        val responseErrorBody = Mockito.mock(ResponseBody::class.java)
        Mockito.`when`(response.errorBody())
            .thenReturn(responseErrorBody)

        Mockito.`when`(responseErrorBody.string())
            .thenReturn(gson.toJson(BaseResponse()))

        assertEquals(null, response.getError())

    }

    @Test
    fun mapErrorFunctionTest() {

        val response = Mockito.mock(Response::class.java)
        val responseErrorBody = Mockito.mock(ResponseBody::class.java)
        Mockito.`when`(response.errorBody())
            .thenReturn(responseErrorBody)

        val baseResponse = BaseResponse()
        baseResponse.baseError = BaseError(404, "Not found")

        Mockito.`when`(responseErrorBody.string())
            .thenReturn(gson.toJson(baseResponse))

        assertEquals(BaseError(404, "Not found"), response.getError())

    }
}