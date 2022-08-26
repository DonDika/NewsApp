package com.dondika.newsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import com.dondika.newsapp.data.repository.NewsRepository

class BookmarkViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

}