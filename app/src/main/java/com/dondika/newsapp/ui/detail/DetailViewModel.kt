package com.dondika.newsapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dondika.newsapp.data.local.entity.NewsEntity
import com.dondika.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(private val newsRepository: NewsRepository): ViewModel() {

    fun saveNews(news: NewsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.setNewsBookmarked(news, true)
        }
    }

    fun deleteNews(news: NewsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.setNewsBookmarked(news, false)
        }
    }

}