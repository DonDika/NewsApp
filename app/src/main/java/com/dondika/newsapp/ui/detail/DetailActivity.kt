package com.dondika.newsapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import com.dondika.newsapp.R
import com.dondika.newsapp.data.local.entity.NewsEntity
import com.dondika.newsapp.databinding.ActivityDetailBinding
import com.dondika.newsapp.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this@DetailActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWebView()
    }

    private fun setWebView() {
        val news = intent.getParcelableExtra<NewsEntity>(EXTRA_NEWS) as NewsEntity

        binding.apply {
            webViewNews.apply {
                clearCache(true)
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    useWideViewPort = true
                    domStorageEnabled = true
                }
                loadUrl(news.url)
            }

            if (news.isBookmarked == true){
                isBookmarked(true)
            } else {
                isBookmarked(false)
            }

            fabAddBookmarked.setOnClickListener {
                if (news.isBookmarked == true){
                    news.isBookmarked = false
                    detailViewModel.deleteNews(news)
                    Toast.makeText(this@DetailActivity, "Berita dihapus dari bookmark", Toast.LENGTH_SHORT).show()
                    isBookmarked(false)

                } else {
                    news.isBookmarked = true
                    news.let { it1 ->
                        detailViewModel.saveNews(it1)
                        isBookmarked(true)
                        Toast.makeText(this@DetailActivity, "Berita disimpan di bookmark", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun isBookmarked(mark: Boolean){
        if (mark){
            binding.fabAddBookmarked.setImageResource(R.drawable.ic_bookmarked)
        } else {
            binding.fabAddBookmarked.setImageResource(R.drawable.ic_bookmark)
        }
    }

    companion object {
        const val EXTRA_NEWS = "extra_news"
    }
}