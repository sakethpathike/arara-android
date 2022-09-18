package com.sakethh.arara.unreleased.currentMusicScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sakethh.arara.R
import com.sakethh.arara.unreleased.UnreleasedViewModel

class CurrentMusicScreenViewModel():ViewModel() {
    val currentIsPauseIcon= mutableStateOf(true) // defines what icon is current
    val currentPlayPauseIcons= mutableListOf(R.drawable.play_icon_current_music_screen,R.drawable.pause_icon_current_music_screen)  // defines icon sources
    val descriptionButtonClicked= mutableStateOf(false)
}