package com.sakethh.arara.unreleased

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class UnreleasedScreenCurrentData(
    val currentSongName: String,
    val currentImgUrl: String,
    val currentLyrics: String,
    val songDescription: String,
    val descriptionBy: String,
    val descriptionOrigin: String,
    val artworkBy:String
) : Parcelable
