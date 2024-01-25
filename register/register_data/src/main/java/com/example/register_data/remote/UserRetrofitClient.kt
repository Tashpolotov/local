package com.example.register_data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserRetrofitClient {

    fun createApiService(): UserApiService{
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS).build()

        val retrofitClient = Retrofit.Builder().baseUrl("http://34.172.47.19/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()

        return retrofitClient.create(UserApiService::class.java)
    }
}
