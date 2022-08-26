package com.dondika.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dondika.newsapp.BuildConfig
import com.dondika.newsapp.data.local.entity.NewsEntity
import com.dondika.newsapp.data.local.room.NewsDao
import com.dondika.newsapp.data.remote.api.ApiService
import com.dondika.newsapp.data.remote.response.NewsResponse
import com.dondika.newsapp.utils.AppExecutors
import com.dondika.newsapp.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository private constructor (
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>>{
        result.value = Result.Loading
        val client = apiService.getNews(BuildConfig.API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    val newsList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { article ->
                            val isBookmarked = newsDao.isNewsBookmarked(article.title)
                            val news = NewsEntity(
                                article.title,
                                article.publishedAt,
                                article.urlToImage,
                                article.source.name,
                                article.url,
                                isBookmarked
                            )
                            newsList.add(news)
                        }
                        newsDao.deleteAll()
                        newsDao.insertNews(newsList)
                    }
                }
            }
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = newsDao.getNews()
        result.addSource(localData) { newData: List<NewsEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    fun setNewsBookmarked(news: NewsEntity, bookmarkState: Boolean) {
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }

    companion object {
        @Volatile
        private var INSTANCE: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository {
            return INSTANCE ?: synchronized(this){
                NewsRepository(apiService, newsDao, appExecutors).also {
                    INSTANCE = it
                }
            }
        }
    }



}