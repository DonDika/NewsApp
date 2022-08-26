package com.dondika.newsapp.di

import android.content.Context
import com.dondika.newsapp.data.local.room.NewsDatabase
import com.dondika.newsapp.data.remote.api.ApiConfig
import com.dondika.newsapp.data.repository.NewsRepository
import com.dondika.newsapp.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): NewsRepository{
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }

}