package com.rinnbie.amiibodb.api

import com.rinnbie.amiibodb.data.AmiiboResponse
import com.rinnbie.amiibodb.data.SeriesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface AmiiboService {

    @GET("amiibo/")
    suspend fun getAllAmiibo(): AmiiboResponse

    @GET("amiiboseries/")
    suspend fun getAllSeries(): SeriesResponse

    companion object {
        private const val BASE_URL = "https://www.amiiboapi.com/api/"

        fun create(): AmiiboService {
            val logger = HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AmiiboService::class.java)
        }
    }
}