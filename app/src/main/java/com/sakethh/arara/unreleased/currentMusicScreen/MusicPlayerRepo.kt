package com.sakethh.arara.unreleased.currentMusicScreen

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sakethh.arara.unreleased.UnreleasedViewModel

class MusicPlayerRepo(
) {
    val mediaPlayer: MediaPlayer = MediaPlayer()
    fun musicPlayer(musicURL: String) {
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build()
        )
        mediaPlayer.setDataSource(musicURL)
        mediaPlayer.prepareAsync()
    }
}