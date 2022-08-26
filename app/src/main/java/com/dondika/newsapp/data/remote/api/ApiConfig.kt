package com.dondika.newsapp.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            //.addInterceptor(ChuckerInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

}




    /*private fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor{
                val original = it.request()
                val requestBuilder = original.newBuilder()
                val resquest = requestBuilder.build()
                it.proceed(resquest)
        }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    fun create(): ApiService{
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }*/

