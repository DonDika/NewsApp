package com.dondika.newsapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news")
data class NewsEntity(

    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String? = null,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo("name")
    val mediaName: String? = null,

    @field:ColumnInfo(name = "url")
    val url: String,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean? = false

): Parcelable