package com.dondika.newsapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dondika.newsapp.R
import com.dondika.newsapp.data.local.entity.NewsEntity
import com.dondika.newsapp.databinding.ActivityHomeBinding
import com.dondika.newsapp.ui.adapter.NewsAdapter
import com.dondika.newsapp.ui.bookmark.BookmarkActivity
import com.dondika.newsapp.ui.detail.DetailActivity
import com.dondika.newsapp.utils.Result
import com.dondika.newsapp.utils.ViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this@HomeActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.bookmark -> {
                startActivity(Intent(this@HomeActivity, BookmarkActivity::class.java))
                true
            }
            else -> false
        }
    }


    private fun setupAdapter() {

        val newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
        }

        newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
            override fun onItemClicked(itemNews: NewsEntity) {
                val news = NewsEntity(
                    itemNews.title,
                    itemNews.publishedAt,
                    itemNews.urlToImage,
                    itemNews.mediaName,
                    itemNews.url,
                    itemNews.isBookmarked
                )
                val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_NEWS, news)
                startActivity(intent)
            }

        })

        homeViewModel.getHeadlineNews().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        val newsData = result.data
                        newsAdapter.setData(newsData)
                    }
                    is Result.Error -> {

                    }
                }
            }
        }
    }



}