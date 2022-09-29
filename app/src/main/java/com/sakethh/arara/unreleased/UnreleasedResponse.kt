package com.sakethh.arara.unreleased

import com.google.gson.annotations.SerializedName

data class UnreleasedResponse(
    @SerializedName("songName")
    val songName:String,
    @SerializedName("specificArtwork")
    val imgURL:String,
    @SerializedName("audioLink_70K")
    val audioLink:String,
    @SerializedName("lyrics")
    val lyrics:String,
    @SerializedName("songDescription")
    val songDescription:String,
    @SerializedName("descriptionBi")
    val descriptionBy:String,
    @SerializedName("descriptionOrigin")
    val descriptionOrigin:String,
    @SerializedName("specificArtworkBi")
    val specificArtworkBy:String,
    @SerializedName("artWorkHDURL")
    val imgURLHD:String
)

data class UnreleasedFooterImage(
    @SerializedName("footerGifURL")
    val footerImg:String
)
data class UnreleasedArtwork(
    @SerializedName("unreleasedHeaderURL")
    val artwork:String
)
data class MusicLoadingGIF(
    @SerializedName("url")
    val gifURL:String
)
data class MusicPlayingGIF(
    @SerializedName("url")
    val gifURL:String
)