package com.dew.newsapplication.model

import com.google.gson.annotations.SerializedName

data class HeadLineRes(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: ArrayList<ArticleInfo>
)