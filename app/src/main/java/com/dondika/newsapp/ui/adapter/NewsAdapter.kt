package com.dondika.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dondika.newsapp.data.local.entity.NewsEntity
import com.dondika.newsapp.databinding.ItemNewsBinding
import com.dondika.newsapp.utils.DateFormatter

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val itemNews = ArrayList<NewsEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(items: List<NewsEntity>) {
        itemNews.clear()
        itemNews.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(itemNews[position])
    }

    override fun getItemCount(): Int {
        return itemNews.size
    }

    inner class ViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemNews: NewsEntity){
            binding.apply {
                Glide.with(itemView)
                    .load(itemNews.urlToImage)
                    .into(binding.newsImage)
                newsDate.text = DateFormatter.formatDate(itemNews.publishedAt!!)
                newsTitle.text = itemNews.title
                newsMedia.text = itemNews.mediaName
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(itemNews)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(itemNews: NewsEntity)
    }

}