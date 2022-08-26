package com.dondika.newsapp.ui.home

import androidx.lifecycle.ViewModel
import com.dondika.newsapp.data.repository.NewsRepository

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getHeadlineNews() = newsRepository.getHeadlineNews()

}