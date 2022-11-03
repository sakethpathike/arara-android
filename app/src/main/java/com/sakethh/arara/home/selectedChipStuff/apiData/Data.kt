package com.sakethh.arara.home.selectedChipStuff.apiData

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("author")
    val author: String,
    @SerializedName("is_video")
    val is_video: Boolean,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)