package com.sakethh.arara.unreleased.currentMusicScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sakethh.arara.R

class CurrentMusicScreenViewModel:ViewModel() {
    val currentIsPauseIcon= mutableStateOf(true) // defines what icon is current
    val currentPlayPauseIcons= mutableListOf(R.drawable.play,R.drawable.pause)  // defines icon sources
}