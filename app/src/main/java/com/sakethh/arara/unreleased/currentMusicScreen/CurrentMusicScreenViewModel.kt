package com.sakethh.arara.unreleased.currentMusicScreen

import android.text.format.DateUtils
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.R
import com.sakethh.arara.unreleased.UnreleasedViewModel
import com.sakethh.arara.unreleased.currentMusicScreen.CurrentMusicScreenViewModel.CurrentMusicScreenUtils.isBtmSheetCollapsed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Suppress("LocalVariableName")
class CurrentMusicScreenViewModel(private val unreleasedUtils: UnreleasedViewModel.UnreleasedUtils = UnreleasedViewModel.UnreleasedUtils) :
    ViewModel() {
    val currentIsPauseIcon = mutableStateOf(true) // defines what icon is current
    val currentPlayPauseIcons = mutableListOf(
        R.drawable.play_icon_current_music_screen,
        R.drawable.pause_icon_current_music_screen
    )  // defines icon sources
    val currentImageGifIcons =
        mutableListOf(R.drawable.image, R.drawable.video_icon)  // defines icon sources
    val currentIsImageIcon = mutableStateOf(true) // defines what icon is current
    val descriptionButtonClicked = mutableStateOf(false)
    val isCurrentIconPause = unreleasedUtils.rememberMusicPlayerControl


    object CurrentMusicScreenUtils {
        val isBtmSheetCollapsed = mutableStateOf(false)
    }

    fun currentDurationFlow(): Flow<Any> {
        return flow {
            while (!UnreleasedViewModel.UnreleasedUtils.musicCompleted.value && !isBtmSheetCollapsed.value) {
                delay(1000L)
                emit(
                    DateUtils.formatElapsedTime((UnreleasedViewModel.UnreleasedUtils.mediaPlayer.currentPosition / 1000).toLong())
                        .toString()
                )
            }
        }
    }

    fun currentDurationFloatFlow(): Flow<Any> {
        return flow {
            while (!UnreleasedViewModel.UnreleasedUtils.musicCompleted.value && !isBtmSheetCollapsed.value) {
                delay(1000L)
                emit(
                    UnreleasedViewModel.UnreleasedUtils.mediaPlayer.currentPosition
                )
            }
        }
    }

    fun isMusicPlaying(): Flow<Any> {
        return flow {
            if (UnreleasedViewModel.UnreleasedUtils.mediaPlayer.duration != 0) {
                while (true) {
                    delay(1000L)
                    if (!UnreleasedViewModel.UnreleasedUtils.mediaPlayer.isPlaying) {
                        emit(false)
                    } else {
                        emit(true)
                    }
                }
            }
        }
    }

    fun actualDuration(): Flow<Any> {
        return flow {
            while (!UnreleasedViewModel.UnreleasedUtils.musicCompleted.value && !isBtmSheetCollapsed.value) {
                delay(1000L)
                val _actualDuration =
                    UnreleasedViewModel.UnreleasedUtils.mediaPlayer.duration
                emit(
                    DateUtils.formatElapsedTime(_actualDuration.toLong() / 1000).toString()
                )
                delay(1000L)
            }
        }
    }

}


