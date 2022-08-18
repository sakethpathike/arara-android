package com.sakethh.arara.unreleased

import com.google.gson.annotations.SerializedName

data class UnreleasedResponse(
    @SerializedName("songName")
    val songName:String,
    @SerializedName("specificArtwork")
    val imgURL:String
)

data class UnreleasedFooterImage(
    @SerializedName("footerGifURL")
    val footerImg:String
)
data class UnreleasedArtwork(
    @SerializedName("unreleasedHeaderURL")
    val artwork:String
)