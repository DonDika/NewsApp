package com.dondika.newsapp.data.remote.api

import com.dondika.newsapp.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=id")
    fun getNews(@Query("apiKey") apiKey: String): Call<NewsResponse>

    //&apiKey=afb60d53ed054c7e9fb03245e6e0e5ad

}