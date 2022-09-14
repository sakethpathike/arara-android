package com.sakethh.arara.unreleased

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import kotlinx.parcelize.Parcelize

@Parcelize
class UnreleasedScreenCurrentData(
    val currentSongName: String,
    val currentImgUrl: String,
    val currentLyrics: String
) : Parcelable
