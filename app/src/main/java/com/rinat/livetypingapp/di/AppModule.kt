package com.rinat.livetypingapp.di

import com.rinat.livetypingapp.network.NetworkRepository
import com.rinat.livetypingapp.network.api.BookApi
import com.rinat.livetypingapp.router.MainRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl(): String = "https://www.googleapis.com/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideBookApi(retrofit: Retrofit): BookApi = retrofit.create(BookApi::class.java)

    @Provides
    @Singleton
    fun provideNetworkDataSource(bookApi: BookApi): NetworkRepository = NetworkRepository(bookApi)

    @Provides
    @Singleton
    fun providesMainRouter() = MainRouter()

}