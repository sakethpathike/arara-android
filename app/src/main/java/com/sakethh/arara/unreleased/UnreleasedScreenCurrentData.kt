package com.sakethh.arara.unreleased

import android.media.MediaPlayer
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class UnreleasedScreenCurrentData(
    val currentSongName: String,
    val currentImgUrl: String,
    val currentLyrics: String,
    val songDescription: String,
    val descriptionBy: String,
    val descriptionOrigin: String,
    val artworkBy:String,
    val isPlaying:Boolean
) : Parcelable
