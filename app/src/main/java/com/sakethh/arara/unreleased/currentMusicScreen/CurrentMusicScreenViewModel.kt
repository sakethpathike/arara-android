package com.sakethh.arara.unreleased.currentMusicScreen

import android.text.format.DateUtils
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.R
import com.sakethh.arara.unreleased.UnreleasedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Suppress("LocalVariableName")
class CurrentMusicScreenViewModel(private val unreleasedViewModel: UnreleasedViewModel = UnreleasedViewModel()) :
    ViewModel() {
    private val _actualDuration = UnreleasedViewModel.UnreleasedUtils.mediaPlayer.duration
    val actualDuration = DateUtils.formatElapsedTime(_actualDuration.toLong() / 1000).toString()
    val currentIsPauseIcon = mutableStateOf(true) // defines what icon is current
    val currentPlayPauseIcons = mutableListOf(
        R.drawable.play_icon_current_music_screen,
        R.drawable.pause_icon_current_music_screen
    )  // defines icon sources
    val currentImageGifIcons =
        mutableListOf(R.drawable.image, R.drawable.video_icon)  // defines icon sources
    val currentIsImageIcon = mutableStateOf(true) // defines what icon is current
    val descriptionButtonClicked = mutableStateOf(false)
    val isCurrentIconPause = UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerControl

    init {
        currentDuration()
    }

    fun currentDuration() {
        val durationFlow = flow {
            emit(
                DateUtils.formatElapsedTime((UnreleasedViewModel.UnreleasedUtils.mediaPlayer.currentPosition / 1000).toLong())
                    .toString()
            )
        }
        viewModelScope.launch {
            durationFlow.collect {
                Log.d("FLOW_LOGS", it)
            }
        }
    }
}