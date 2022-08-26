package com.dondika.newsapp.ui.bookmark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dondika.newsapp.R
import com.dondika.newsapp.data.local.entity.NewsEntity
import com.dondika.newsapp.databinding.ActivityBookmarkBinding
import com.dondika.newsapp.ui.adapter.NewsAdapter
import com.dondika.newsapp.ui.detail.DetailActivity
import com.dondika.newsapp.utils.Result
import com.dondika.newsapp.utils.ViewModelFactory

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    private val bookmarkViewModel: BookmarkViewModel by viewModels {
        ViewModelFactory.getInstance(this@BookmarkActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
    }

    private fun setupAdapter() {
        val newsAdapter = NewsAdapter()

        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@BookmarkActivity, LinearLayoutManager.VERTICAL, false)
        }

        newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback{
            override fun onItemClicked(itemNews: NewsEntity) {
                val news = NewsEntity(
                    itemNews.title,
                    itemNews.publishedAt,
                    itemNews.urlToImage,
                    itemNews.mediaName,
                    itemNews.url,
                    itemNews.isBookmarked
                )
                val intent = Intent(this@BookmarkActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_NEWS, news)
                startActivity(intent)
            }
        })

        bookmarkViewModel.getBookmarkedNews().observe(this@BookmarkActivity){ bookmarkedNews ->
            newsAdapter.setData(bookmarkedNews)
        }

    }


}