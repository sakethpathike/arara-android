package com.sakethh.arara.unreleased.musicPlayer

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.unreleased.currentMusicScreen.MusicPlayerRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicPlayerViewModel(private val musicURL:String, musicPlayerRepo: MusicPlayerRepo = MusicPlayerRepo()) : ViewModel() {
    private val coroutineExceptionHandler= CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            MusicPlayerRepo().musicPlayer(musicURL)
        }
    }
    val musicPlayerControl = musicPlayerRepo.mediaPlayer
}