package com.rinnbie.amiibodb.api

interface AmiiboService {

    /*companion object {
        private const val BASE_URL = "https://api.unsplash.com/"

        fun create(): AmiiboService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UnsplashService::class.java)
        }
    }*/
}